package com.example.linkapp.model

sealed class LinkType {
    abstract val name: String
    abstract val autoConnect: Boolean
    abstract val highLatency: Boolean

    data class UDP(
        override val name: String,
        override val autoConnect: Boolean,
        override val highLatency: Boolean,
        val port: Int,
        val serverAddresses: List<String>
    ) : LinkType()

    data class Serial(
        override val name: String,
        override val autoConnect: Boolean,
        override val highLatency: Boolean,
        val serialPort: String,
        val baudRate: Int
    ) : LinkType()
}