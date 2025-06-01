package com.example.linkapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import com.example.linkapp.viemodel.LinkViewModelNew

@Composable
fun MainScreen(viewModel: LinkViewModelNew, modifier: Modifier) {
    val links by viewModel.links.collectAsState()
    val dialogState by viewModel.dialogState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        links.forEach {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(it.name)
                Button(onClick = { }) {
                    Text("Connect")
                }
            }
        }
        Button(onClick = { viewModel.showDialog("UDP") }) {
            Text("Add")
        }
    }

    dialogState?.let {
        AddLinkDialog(
            link = it,
            onSave = {
                viewModel.addLink(it)
                viewModel.dismissDialog()
            },
            onCancel = { viewModel.dismissDialog() }
        )
    }
}