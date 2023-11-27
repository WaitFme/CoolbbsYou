package com.anpe.coolbbsyou.ui.host.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.ui.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun PostScreen(navControllerScreen: NavHostController, viewModel: MainViewModel) {
    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
        val scope = rememberCoroutineScope()

        val createFeedState by viewModel.createFeedState.collectAsState()

//        val hostState = SnackbarHostState()
        val hostState by remember {
            mutableStateOf(SnackbarHostState())
        }

        val messages by remember {
            mutableStateOf(createFeedState.message)
        }

        var message by remember {
            mutableStateOf("")
        }

        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            topBar = {
                TopBar(
                    onBack = { navControllerScreen.popBackStack() },
                    onSend = {
                        scope.launch {
                            postMessage(message, viewModel)
                            println(createFeedState)
                            if (createFeedState.status == 401) {
                                hostState.showSnackbar(messages)
                            }
                        }
                    }
                )
            },
            content = { pv ->
                Column(modifier = Modifier.padding(pv)) {
                    Content(
                        onValueChange = {
                            message = it
                        },
                        onSend = {
                            scope.launch {
                                postMessage(message, viewModel)
                            }
                        }
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onBack: () -> Unit, onSend: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Post Something") },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
        },
        actions = {
            IconButton(onClick = onSend) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "back")
            }
        }
    )
}

@Composable
private fun Content(onValueChange: (String) -> Unit, onSend: () -> Unit) {
    var message by remember {
        mutableStateOf(TextFieldValue())
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.secondary),
        value = message,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(onSend = {
            onSend()
        }),
        onValueChange = {
            message = it
            onValueChange(it.text)
        },
    )
}

private suspend fun postMessage(message: String, viewModel: MainViewModel) {
    if (message.isNotEmpty()) {
        viewModel.channel.send(MainEvent.CreateFeed(message))
    }
}