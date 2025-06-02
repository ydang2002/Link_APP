package com.example.linkapp.viemodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import com.example.linkapp.common.AppLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LanguageViewModel : ViewModel() {

    private val _currentLanguage = MutableStateFlow(
        AppLanguage.fromTag(AppCompatDelegate.getApplicationLocales().toLanguageTags())
    )
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage

    fun changeLanguage(language: AppLanguage) {
        if (_currentLanguage.value != language) {
            _currentLanguage.value = language
            val localeList = LocaleListCompat.forLanguageTags(language.languageTag)
            AppCompatDelegate.setApplicationLocales(localeList)
        }
    }
}