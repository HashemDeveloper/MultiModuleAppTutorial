package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
    loadingState: Boolean,
    messageBarState: MessageBarState,
    onSignInClicked: () -> Unit,
    onTokenIdReceived: (String) -> Unit,
    onDialogDismissed: (String) -> Unit
) {
    Scaffold(content = {
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
}