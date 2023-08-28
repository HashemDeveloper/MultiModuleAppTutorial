package com.jrsoft.jetpack.tutorial.multimoduleapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.components.DisplayAlertDialog
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.auth.AuthenticationScreen
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.auth.AuthenticationViewModel
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.home.HomeScreen
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens.write.WriteScreen
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.MONGO_DB_APP_ID
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.WRITE_SCREEN_DIARY_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetupNavigationGraph(startDestination: String, navController: NavHostController) {
    NavHost(startDestination = startDestination, navController = navController) {
        authenticationRoute(navigateToHome = {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        })
        homeRoute(navigateToWriteScreen = {
            navController.navigate(Screen.Write.route)
        }, navigateToAuth = {
            navController.popBackStack()
            navController.navigate(Screen.Authentication.route)
        })
        writeRoute(onBackPressed = { navController.popBackStack() })
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
                viewModel.signingWithMongoAtlas(tokenId = tokenId, onSuccess = {
                    messageBarState.addSuccess("Successfully Authenticated")
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

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeRoute(navigateToWriteScreen: () -> Unit, navigateToAuth: () -> Unit) {
    composable(route = Screen.Home.route) {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var isSignOutDialogOpened by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        HomeScreen(
            drawerState = drawerState,
            onSignOutClicked = {
                isSignOutDialogOpened = true
            },
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            }, navigateToWriteScreen = navigateToWriteScreen
        )

        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want to sign out from google account?",
            isDialogOpen = isSignOutDialogOpened,
            onCloseDialog = { isSignOutDialogOpened = false },
            onConfirmClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.Companion.create(MONGO_DB_APP_ID).currentUser
                    user?.logOut()
                    withContext(Dispatchers.Main) {
                        navigateToAuth.invoke()
                    }
                }
            })
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
