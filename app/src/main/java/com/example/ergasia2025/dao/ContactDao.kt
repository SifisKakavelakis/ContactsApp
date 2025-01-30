package com.example.ergasia2025.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ergasia2025.model.Contact

// Το interface DAO χρησιμοποιείται για τη διαχείριση της βάσης δεδομένων
@Dao
interface ContactDao {

    // Μέθοδος εισαγωγής επαφής
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    // Μέθοδος διαγραφής επαφής
    @Delete
    suspend fun delete(contact: Contact)

    // Μέθοδος ενημέρωσης μιας επαφής
    @Update
    suspend fun update(contact: Contact)

    // Query για την ανάκτηση όλων των επαφών ταξινομημένων κατά αύξουσα σειρά με βάση το ID
    @Query("Select * from contactsTable order by id ASC")
    fun getAllContacts(): LiveData<List<Contact>>
}