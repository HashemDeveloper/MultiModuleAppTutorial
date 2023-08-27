package com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jrsoft.jetpack.tutorial.multimoduleapp.R
import com.jrsoft.jetpack.tutorial.multimoduleapp.ui.theme.components.GoogleButton

@Composable
fun AuthenticationContent(loadingState: Boolean, onSignInClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(all = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = stringResource(R.string.google_logo)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = stringResource(R.string.welcome_back), fontSize = MaterialTheme.typography.titleLarge.fontSize)
                Text(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    text = stringResource(R.string.please_sign_in_to_continue),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            }
            Column(
                modifier = Modifier.weight(weight = 2f),
                verticalArrangement = Arrangement.Bottom
            ) {
                GoogleButton(loadingState = loadingState, onClick = onSignInClicked)
            }
        }
    }
}

@Composable
@Preview
fun PreviewAuthenticationContent() {
    AuthenticationContent(loadingState = false, onSignInClicked = {})
}