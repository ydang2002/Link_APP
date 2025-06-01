package com.example.linkapp.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkapp.data.LinkEntity
import com.example.linkapp.data.LinkRepository
import com.example.linkapp.model.LinkType


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LinkViewModelNew(private val repository: LinkRepository) : ViewModel() {

    private val _links = MutableStateFlow<List<LinkEntity>>(emptyList())
    val links: StateFlow<List<LinkEntity>> = _links

    private val _dialogState = MutableStateFlow<LinkType?>(null)
    val dialogState: StateFlow<LinkType?> = _dialogState

    init {
        viewModelScope.launch {
            _links.value = repository.getAllLinks()
        }
    }

    fun addLink(link: LinkType) {
        viewModelScope.launch {
            val entity = when (link) {
                is LinkType.UDP -> LinkEntity(
                    name = link.name,
                    autoConnect = link.autoConnect,
                    highLatency = link.highLatency,
                    type = "UDP",
                    port = link.port,
                    serverAddresses = link.serverAddresses.joinToString(",")
                )
                is LinkType.Serial -> LinkEntity(
                    name = link.name,
                    autoConnect = link.autoConnect,
                    highLatency = link.highLatency,
                    type = "Serial",
                    serialPort = link.serialPort,
                    baudRate = link.baudRate
                )
            }
            repository.insertLink(entity)
            _links.value = repository.getAllLinks()
        }
    }

    fun showDialog(type: String) {
        _dialogState.value = when (type) {
            "UDP" -> LinkType.UDP("", false, false, 0, emptyList())
            "Serial" -> LinkType.Serial("", false, false, "", 57600)
            else -> null
        }
    }

    fun dismissDialog() {
        _dialogState.value = null
    }
}