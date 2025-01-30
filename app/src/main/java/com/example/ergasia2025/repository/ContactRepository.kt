package com.example.ergasia2025.repository

import com.example.ergasia2025.dao.ContactDao
import com.example.ergasia2025.model.Contact
import androidx.lifecycle.LiveData

class ContactRepository(private val ContactDao: ContactDao) {

    // Δημιουργια λίστας με όλες τις επαφές απο το ContactDao
    val allContact: LiveData<List<Contact>> = ContactDao.getAllContacts()

    // Μέθοδος για εισαγωγή επαφής
    suspend fun insert(contact: Contact) {
        ContactDao.insert(contact)
    }

    // Μέθοδος για διαγραφή επαφής
    suspend fun delete(contact: Contact) {
        ContactDao.delete(contact)
    }

    // Μέθοδος για ενημέρωση επαφής
    suspend fun update(contact: Contact) {
        ContactDao.update(contact)
    }
}