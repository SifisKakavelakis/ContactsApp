package com.example.ergasia2025.viewmodel

import com.example.ergasia2025.dao.ContactDatabase
import com.example.ergasia2025.model.Contact
import com.example.ergasia2025.repository.ContactRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

// Το ViewModel διαχειρίζεται δεδομένα επαφών
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    // Ζωντανά δεδομένα όλων των επαφών
    val allContact: LiveData<List<Contact>>
    val repository: ContactRepository

    init {
        val dao = ContactDatabase.getDatabase(application).getContactsDao()
        repository = ContactRepository(dao)
        allContact = repository.allContact
    }

    // Διαγραφή επαφής. Καλούμε την μέθοδο διαγραφής από το repository
    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }

    // Ενημέρωση επαφής
    fun updateContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(contact)
    }

    // Προσθήκη νέας επαφής
    fun addContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }
}
