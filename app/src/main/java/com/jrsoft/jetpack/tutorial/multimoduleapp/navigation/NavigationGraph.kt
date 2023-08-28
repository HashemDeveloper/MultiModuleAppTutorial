package com.jrsoft.jetpack.tutorial.multimoduleapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.auth.AuthenticationScreen
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.auth.AuthenticationViewModel
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.write.WriteScreen
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.MONGO_DB_APP_ID
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.WRITE_SCREEN_DIARY_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SetupNavigationGraph(startDestination: String, navController: NavHostController) {
    NavHost(startDestination = startDestination, navController = navController) {
        authenticationRoute(navigateToHome = {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        })
        homeRoute()
        writeRoute(onBackPressed = { navController.popBackStack()})
    }
}

fun NavGraphBuilder.authenticationRoute(navigateToHome: () -> Unit) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        AuthenticationScreen(
            state = oneTapState,
            authenticated = authenticated,
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
                    }
                    viewModel.setLoading(false)
                }, onError = { error ->
                    messageBarState.addError(error)
                    viewModel.setLoading(false)
                })
            },
            onDialogDismissed = { ex ->
                messageBarState.addError(Exception(ex))
                viewModel.setLoading(false)
            }, navigateToHome = {
                navigateToHome.invoke()
            })
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        val scope = rememberCoroutineScope()
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    App.Companion.create(MONGO_DB_APP_ID).currentUser?.logOut()
                }
            }) {
                Text(text = "Logout")
            }
        }
    }
}

fun NavGraphBuilder.writeRoute(onBackPressed: () -> Unit) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_DIARY_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        WriteScreen(onBackPressed = onBackPressed)
    }
}
