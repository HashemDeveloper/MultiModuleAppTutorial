package com.jrsoft.jetpack.tutorial.multimoduleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.jrsoft.jetpack.tutorial.multimoduleapp.navigation.Screen
import com.jrsoft.jetpack.tutorial.multimoduleapp.navigation.SetupNavigationGraph
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.MultiModuleAppTheme
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants.MONGO_DB_APP_ID
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EntryPoint()
        }
    }
}

@Composable
private fun EntryPoint() {
    MultiModuleAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()
            SetupNavigationGraph(
                startDestination = getStartDestination(),
                navController = navController
            )
        }
    }
}
private fun getStartDestination(): String {
    val user = App.Companion.create(MONGO_DB_APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.Home.route else Screen.Authentication.route
}

@Preview(showBackground = true)
@Composable
fun MultiModuleAppPreview() {
    EntryPoint()
}
