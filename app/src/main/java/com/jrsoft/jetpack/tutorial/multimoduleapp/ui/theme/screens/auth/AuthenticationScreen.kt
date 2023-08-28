package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    state: OneTapSignInState,
    authenticated: Boolean,
    loadingState: Boolean,
    messageBarState: MessageBarState,
    onSignInClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToHome: () -> Unit,
) {
    Scaffold(
        // In theme.kt file we've inverse !darkTheme to prevent overlapping the device status bar on top
        // and device bottom navigation. By applying background with surface will prevent that from
        // overlapping
        modifier = Modifier.navigationBarsPadding().statusBarsPadding().background(MaterialTheme.colorScheme.surface),
        content = {
        ContentWithMessageBar(messageBarState = messageBarState) {
            AuthenticationContent(loadingState = loadingState, onSignInClicked = onSignInClicked)
        }
    })
    OneTapSignInWithGoogle(
        state = state,
        clientId = Constants.GOOGLE_AUTH_CLIENT_ID,
        onTokenIdReceived = { tokeInd ->
            onTokenIdReceived.invoke(tokeInd)
        },
        onDialogDismissed = { message ->
            onDialogDismissed.invoke(message)
        })
    LaunchedEffect(key1 = authenticated) {
        if (authenticated) {
            navigateToHome.invoke()
        }
    }
}