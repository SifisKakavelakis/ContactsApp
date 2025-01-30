package com.example.ergasia2025.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Δηλώση του ονοματος του πίνακα
@Entity(tableName = "contactsTable")
class Contact (
    @ColumnInfo(name = "Name") val contactName: String,
    @ColumnInfo(name = "LastName") val contactLastName: String,
    @ColumnInfo(name = "Email") val contactEmail: String,
    @ColumnInfo(name = "Phone") val contactPhone: String,
    @ColumnInfo(name = "timestamp") val timeStamp: String
) {
    // Πρωτεύον κλειδί
    @PrimaryKey(autoGenerate = true) var id = 0
}