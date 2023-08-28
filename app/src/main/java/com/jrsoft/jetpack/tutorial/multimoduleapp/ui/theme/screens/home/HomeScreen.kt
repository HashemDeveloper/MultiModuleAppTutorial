package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onMenuClicked: () -> Unit, navigateToWriteScreen: () -> Unit) {
    Scaffold(
        topBar = {
            HomeTopBar(onMenuClicked = onMenuClicked)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToWriteScreen) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "New Diary Icon"
                )
            }
        },
        content = {})
}