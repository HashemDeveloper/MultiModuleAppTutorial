package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.write

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(onBackPressed: () -> Unit) {
    Scaffold(topBar = {
        WriteTopBar(onBackPressed = onBackPressed)
    }, content = {

    })
}

@Composable
@Preview
fun PreviewWriteScreen() {
    WriteScreen {}
}