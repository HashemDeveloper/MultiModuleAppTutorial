package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(loadingState: Boolean, onSignInClicked: () -> Unit) {
    Scaffold(content = {
        AuthenticationContent(loadingState = loadingState, onSignInClicked = onSignInClicked)
    })
}