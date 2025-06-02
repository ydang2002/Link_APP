package com.example.linkapp.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.linkapp.R

@Composable
fun LanguagePickerScreen() {
    val languages = listOf(
        "en" to stringResource(R.string.english),
        "vi" to stringResource(R.string.vietnamese),
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = stringResource(R.string.greeting),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.choose_language),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        languages.forEach { (langCode, langLabel) ->
            Button(
                onClick = { setAppLocale(langCode) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(langLabel)
            }
        }
    }
}

fun setAppLocale(languageTag: String) {
    val appLocale = LocaleListCompat.forLanguageTags(languageTag)
    AppCompatDelegate.setApplicationLocales(appLocale)
}