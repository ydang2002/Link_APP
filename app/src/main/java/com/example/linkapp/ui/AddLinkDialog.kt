package com.example.linkapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


import androidx.compose.ui.window.Dialog
import com.example.linkapp.model.LinkType

@Composable
fun AddLinkDialog(
    link: LinkType,
    onSave: (LinkType) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(link.name) }
    var autoConnect by remember { mutableStateOf(link.autoConnect) }
    var highLatency by remember { mutableStateOf(link.highLatency) }
    var selectedType by remember { mutableStateOf(if (link is LinkType.Serial) "Serial" else "UDP") }

    //test git rebase

    Dialog(onDismissRequest = onCancel) {
        // TEST GIT
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Add New Link", style = MaterialTheme.typography.titleLarge)
                    Row {
                        TextButton(onClick = onCancel) { Text("Cancel") }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = {
                            when (selectedType) {
                                "UDP" -> {
                                    val udp = link as? LinkType.UDP
                                    onSave(
                                        LinkType.UDP(
                                            name,
                                            autoConnect,
                                            highLatency,
                                            udp?.port ?: 0,
                                            udp?.serverAddresses ?: emptyList()
                                        )
                                    )
                                }
                                "Serial" -> {
                                    val serial = link as? LinkType.Serial
                                    onSave(
                                        LinkType.Serial(
                                            name,
                                            autoConnect,
                                            highLatency,
                                            serial?.serialPort ?: "",
                                            serial?.baudRate ?: 57600
                                        )
                                    )
                                }
                            }
                        }) {
                            Text("Save")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter name") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // AutoConnect
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Automatically Connect on Start")
                    Switch(checked = autoConnect, onCheckedChange = { autoConnect = it })
                }

                // HighLatency
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("High Latency")
                    Switch(checked = highLatency, onCheckedChange = { highLatency = it })
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Type dropdown
                    Text("Type")
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        OutlinedButton(onClick = { expanded = true }) {
                            Text(selectedType)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("UDP") },
                                onClick = {
                                    selectedType = "UDP"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Serial") },
                                onClick = {
                                    selectedType = "Serial"
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Note
                if (selectedType == "UDP") {
                    Text(
                        text = "Note: For best performance, please disable AutoConnect to UDP devices on the General page.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Type-specific UI
                if (selectedType == "UDP") {
                    var port by remember { mutableStateOf((link as? LinkType.UDP)?.port?.toString() ?: "0") }
                    var address by remember { mutableStateOf("") }
                    var serverList by remember { mutableStateOf((link as? LinkType.UDP)?.serverAddresses ?: emptyList()) }

                    OutlinedTextField(
                        value = port,
                        onValueChange = { port = it },
                        label = { Text("Port") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Server Addresses (optional)")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Example: 127.0.0.1:14550") }
                        )
                        Button(onClick = {
                            if (address.isNotBlank()) {
                                serverList = serverList + address
                                address = ""
                            }
                        }) {
                            Text("Add Server")
                        }
                    }
                } else if (selectedType == "Serial") {
                    var serialPort by remember { mutableStateOf((link as? LinkType.Serial)?.serialPort ?: "") }
                    var baudRate by remember { mutableStateOf((link as? LinkType.Serial)?.baudRate?.toString() ?: "57600") }

                    OutlinedTextField(
                        value = serialPort,
                        onValueChange = { serialPort = it },
                        label = { Text("Serial Port") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = baudRate,
                        onValueChange = { baudRate = it },
                        label = { Text("Baud Rate") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
    }
}
