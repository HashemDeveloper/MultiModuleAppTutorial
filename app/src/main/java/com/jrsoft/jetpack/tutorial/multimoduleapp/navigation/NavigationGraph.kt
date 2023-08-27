package com.jrsoft.jetpack.tutorial.multimoduleapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.AuthenticationScreen
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.AuthenticationViewModel
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.WRITE_SCREEN_DIARY_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetupNavigationGraph(startDestination: String, navController: NavHostController) {
    NavHost(startDestination = startDestination, navController = navController) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute() {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        AuthenticationScreen(
            state = oneTapState,
            loadingState = loadingState,
            messageBarState = messageBarState,
            onSignInClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onTokenIdReceived = { tokenId ->
                viewModel.signingWithMongoAtlas(tokenId = tokenId, onSuccess = { isSuccess ->
                    if (isSuccess) {
                        messageBarState.addSuccess("Successfully Authenticated")
                        viewModel.setLoading(false)
                    }
                }, onError = { error ->
                    messageBarState.addError(error)
                    viewModel.setLoading(false)
                })
            },
            onDialogDismissed = { ex ->
                messageBarState.addError(Exception(ex))
                viewModel.setLoading(false)
            })
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {

    }
}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_DIARY_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}
