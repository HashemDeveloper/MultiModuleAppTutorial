package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrsoft.jetpack.tutorial.multimoduleapp.utils.Constants
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {
    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    fun signingWithMongoAtlas(
        tokenId: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    // this works but in Mongo Atlas is not able fetch auth providers
                    // meaning not able to get user details e.g name, picture, email etc
                    // to be able to fix that we're going to be using JWT token provider
//                    App.Companion.create(Constants.MONGO_DB_APP_ID)
//                        .login(Credentials.google(tokenId, GoogleAuthType.ID_TOKEN)).loggedIn

                    App.Companion.create(Constants.MONGO_DB_APP_ID)
                        .login(Credentials.jwt(tokenId)).loggedIn
                }
                withContext(Dispatchers.Main) {
                    onSuccess.invoke(result)
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    onError.invoke(ex)
                }
            }
        }
    }
}