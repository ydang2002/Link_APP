package com.example.linkapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.linkapp.R
import com.example.linkapp.common.AppLanguage
import com.example.linkapp.viemodel.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagePickerScreen(viewModel: LanguageViewModel) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("In-app Language Picker") },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(text = stringResource(id = R.string.greeting), style = MaterialTheme.typography.headlineMedium)

            LanguageDropdown(currentLanguage = currentLanguage, onLanguageSelected = {
                viewModel.changeLanguage(it)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdown(currentLanguage: AppLanguage, onLanguageSelected: (AppLanguage) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = stringResource(id = currentLanguage.displayNameResId),
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(id = R.string.choose_language)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            AppLanguage.values().forEach { lang ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = lang.displayNameResId)) },
                    onClick = {
                        onLanguageSelected(lang)
                        expanded = false
                    }
                )
            }
        }
    }
}

