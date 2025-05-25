package com.example.linkapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val autoConnect: Boolean,
    val highLatency: Boolean,
    val type: String, // "UDP" or "Serial"
    val port: Int? = null,
    val serverAddresses: String? = null,
    val serialPort: String? = null,
    val baudRate: Int? = null
)