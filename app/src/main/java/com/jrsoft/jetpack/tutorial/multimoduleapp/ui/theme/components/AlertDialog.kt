package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    isDialogOpen: Boolean,
    onCloseDialog: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    if (isDialogOpen) {
        AlertDialog(
            title = {
                Text(
                    text = title, fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message, fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            onDismissRequest = onCloseDialog,
            confirmButton = {
                Button(onClick = {
                    onConfirmClicked.invoke()
                    onCloseDialog.invoke()
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onCloseDialog) {
                    Text(text = "No")
                }
            }
        )
    }

}