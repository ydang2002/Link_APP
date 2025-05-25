package com.example.linkapp.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linkapp.data.LinkRepository

class LinkViewModelFactory(
    private val repository: LinkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LinkViewModel::class.java)) {
            return LinkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}