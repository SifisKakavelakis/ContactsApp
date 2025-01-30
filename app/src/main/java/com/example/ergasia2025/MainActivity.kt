package com.example.ergasia2025

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.ergasia2025.model.Contact
import com.example.ergasia2025.viewmodel.ContactClickDeleteInterface
import com.example.ergasia2025.viewmodel.ContactClickInterface
import com.example.ergasia2025.viewmodel.ContactRVAdapter
import com.example.ergasia2025.viewmodel.ContactViewModel

// Κλάση της εφαρμογής για την εμφάνιση και διαχείριση επαφών που κληρονομεί και απο τις ContactClickInterface, ContactClickDeleteInterface.
class MainActivity : AppCompatActivity(), ContactClickInterface, ContactClickDeleteInterface {

    lateinit var viewModel: ContactViewModel
    lateinit var contactsRV: RecyclerView
    lateinit var addIB: ImageButton
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Αρχικοποιούμε τις μεταβλητές των views
        contactsRV = findViewById(R.id.contactsRV)
        addIB = findViewById(R.id.idIB)
        searchView = findViewById(R.id.searchView)

        // ορίζουμε τον layout
        contactsRV.layoutManager = LinearLayoutManager(this)

        val contactRVAdapter = ContactRVAdapter(this, this, this)
        contactsRV.adapter = contactRVAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ContactViewModel::class.java)

        // Παρατηρητής για ενημέρωση της λίστας επαφών
        viewModel.allContact.observe(this, Observer { list ->
            list?.let {
                viewModel.allContact.observe(this, Observer { list ->
                    list?.let {
                        val sortedList = it.sortedBy { contact -> contact.contactName.lowercase() }
                        contactRVAdapter.updateList(sortedList)
                    }
                })

            }
        })

        // Click listener για προσθήκη νέας επαφής
        addIB.setOnClickListener {
            val intent = Intent(this, AddEditContactActivity::class.java)
            startActivity(intent)
        }

        // Ορίζουμε το SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    filterContacts(it)
                }
                return true
            }

            /// Μέθοδος που καλείται όταν αλλάζει το κείμενο στο πεδίο αναζήτησης
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    filterContacts(it)
                }
                return true
            }
        })
    }

    // Μέθοδος ταξινομησης επαφών
    private fun filterContacts(query: String) {
        val filteredList = ArrayList<Contact>()

        // Ελέγχουμε όλες τις επαφές και τις φιλτράρουμε με βάση το query
        viewModel.allContact.value?.forEach {
            if (it.contactName.contains(query, ignoreCase = true) ||
                it.contactLastName.contains(query, ignoreCase = true) ||
                it.contactPhone.contains(query, ignoreCase = true)) {
                filteredList.add(it)
            }
        }

        // Ενημερώνουμε τον adapter με τη ταξινομήμενη λίστα.
        (contactsRV.adapter as ContactRVAdapter).updateList(filteredList)
    }

    // Μέθοδος που καλείται όταν γίνεται κλικ σε μία επαφή
    override fun onContactClick(contact: Contact) {
        val intent = Intent(this@MainActivity, AddEditContactActivity::class.java)
        intent.putExtra("contactType", "Edit")
        intent.putExtra("contactName", contact.contactName)
        intent.putExtra("contactLastName", contact.contactLastName)
        intent.putExtra("contactPhone", contact.contactPhone)
        intent.putExtra("contactEmail", contact.contactEmail)
        intent.putExtra("contactId", contact.id)
        startActivity(intent)
    }

    // Μέθοδος που καλείται όταν γίνεται κλικ στο κουμπί διαγραφής επαφής
    override fun onDeleteIconClick(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Contact")
        builder.setMessage("Are you sure you want to delete ${contact.contactName} ${contact.contactLastName}?")

        // Επιβεβαίωση διαγραφής
        builder.setPositiveButton("Yes") { dialog, _ ->
            viewModel.deleteContact(contact)
            Toast.makeText(this, "${contact.contactName} ${contact.contactLastName} Deleted", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        // Ακύρωση διαγραφής
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // Δημιουργία και εμφάνιση του AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()
    }
}




