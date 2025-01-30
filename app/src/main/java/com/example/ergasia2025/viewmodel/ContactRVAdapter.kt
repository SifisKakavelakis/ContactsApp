package com.example.ergasia2025.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ergasia2025.R
import com.example.ergasia2025.model.Contact

class ContactRVAdapter(
    val context: Context,
    val contactClickDeleteInterface: ContactClickDeleteInterface,
    val contactClickInterface: ContactClickInterface
) : RecyclerView.Adapter<ContactRVAdapter.ViewHolder>() {

    // Λίστα με όλες τις επαφές
    private val allContact = ArrayList<Contact>()

    // Δημιουργούμε την εσωτερική κλάση view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactTV = itemView.findViewById<TextView>(R.id.idTVContact)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contactTV.text = allContact[position].contactName + " " + allContact[position].contactLastName

        // click listener για την διαγραφή μιας επαφής
        holder.deleteIV.setOnClickListener {
            contactClickDeleteInterface.onDeleteIconClick(allContact[position])
        }

        // click listener για  την επεξεργασία μιας επαφής
        holder.itemView.setOnClickListener {
            contactClickInterface.onContactClick(allContact[position])
        }
    }

    override fun getItemCount(): Int {
        return allContact.size
    }

    // Μέθοδος για ενημέρωση της λίστας επαφών
    fun updateList(newList: List<Contact>) {
        allContact.clear()
        allContact.addAll(newList)
        notifyDataSetChanged()
    }
}

// Διεπαφή για το κλικ στο εικονίδιο διαγραφής
interface ContactClickDeleteInterface {
    fun onDeleteIconClick(contact: Contact)
}

// Διεπαφή για το κλικ στην επαφή
interface ContactClickInterface {
    fun onContactClick(contact: Contact)
}
