package com.example.ergasia2025

import com.example.ergasia2025.model.Contact
import com.example.ergasia2025.viewmodel.ContactViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditContactActivity : AppCompatActivity() {

    // Δημιουργία μεταβλητών για τα στοιχεία διεπαφής χρήστη
    lateinit var contactNameEdt: EditText
    lateinit var contactLastNameEdt: EditText
    lateinit var contactPhoneEdt: EditText
    lateinit var contactEmailEdt: EditText
    lateinit var saveBtn: Button
    lateinit var viewModel: ContactViewModel
    var contactID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        // Αρχικοποίηση του ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ContactViewModel::class.java)

        // Αρχικοποιούμε της μεταβλητες
        contactNameEdt = findViewById(R.id.editTextName)
        contactLastNameEdt = findViewById(R.id.editTextLastname)
        contactPhoneEdt = findViewById(R.id.editTextPhone)
        contactEmailEdt = findViewById(R.id.editTextTextEmail)
        saveBtn = findViewById(R.id.saveBtn)

        // Έλεγχος για την επεξεργασία ή την προσθήκη μιας επαφής
        val contactType = intent.getStringExtra("contactType")
        if (contactType.equals("Edit")) {
            // Ανάκτηση δεδομών επαφής
            val contactName = intent.getStringExtra("contactName")
            val contactLastName = intent.getStringExtra("contactLastName")
            val contactPhone = intent.getStringExtra("contactPhone")
            val contactEmail = intent.getStringExtra("contactEmail")

            contactID = intent.getIntExtra("contactId", -1)
            saveBtn.text = "Update contact"
            contactNameEdt.setText(contactName)
            contactLastNameEdt.setText(contactLastName)
            contactPhoneEdt.setText(contactPhone)
            contactEmailEdt.setText(contactEmail)
        } else {
            saveBtn.text = "Save contact"
        }

        // click listener για το κουμπί αποθήκευσης
        saveBtn.setOnClickListener {
            val contactName = contactNameEdt.text.toString()
            val contactLastName = contactLastNameEdt.text.toString()
            val contactPhone = contactPhoneEdt.text.toString()
            val contactEmail = contactEmailEdt.text.toString()

            // Ενημέρωση ή προσθήκη επαφής ανάλογα με την λειτουργία
            if (contactType.equals("Edit")) {
                if (contactName.isNotEmpty() && contactLastName.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedContact = Contact(contactName, contactLastName, contactEmail, contactPhone, currentDateAndTime)
                    updatedContact.id = contactID
                    viewModel.updateContact(updatedContact)
                    Toast.makeText(this, "Contact Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (contactName.isNotEmpty() && contactLastName.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    viewModel.addContact(Contact(contactName, contactLastName, contactEmail, contactPhone, currentDateAndTime))
                    Toast.makeText(this, "$contactName $contactLastName Added", Toast.LENGTH_LONG).show()
                }
            }

            // Επιστροφή στην MainActivity
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}