package com.anpe.coolbbsyou.ui.host.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.LoginState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToast
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToastString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navControllerScreen: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.login_screen)) },
                navigationIcon = {
                    IconButton(onClick = { navControllerScreen.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { pv ->
            val repository = RemoteRepository()

            val scope = rememberCoroutineScope()

            val context = LocalContext.current

            val loginStatus by viewModel.loginState.collectAsState()

            var requestHash by remember {
                mutableStateOf("")
            }

            LaunchedEffect(key1 = true, block = {
                requestHash = repository.getRequestHash()?: ""

                viewModel.loginState.collect {
                    when (it) {
                        is LoginState.Idle -> { }
                        is LoginState.LoggingIn -> context.showToastString("登陆中，请稍后...")
                        is LoginState.Success -> {
                            (it).apply {
                                if (loginEntity.status == 1) {
//                                    viewModel.sendIntent(MainEvent.GetLoginInfo)
                                    viewModel.sendIntent(MainEvent.GetProfile(loginEntity.sESSION.uid))
                                    navControllerScreen.popBackStack()
                                    context.showToastString("登陆成功！")
                                } else {
                                    context.showToastString("账号或密码错误！")
                                }
                            }
                        }
                        is LoginState.Error -> "登陆失败: ${it.error}".showToast(context)
                    }
                }
            })

            Column(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*Content(
                    navControllerScreen = navControllerScreen,
                    requestHash = requestHash,
                    viewModel = viewModel
                )*/

                var account by remember {
                    mutableStateOf(TextFieldValue())
                }
                var passwd by remember {
                    mutableStateOf(TextFieldValue())
                }

                OutlinedTextField(
                    modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 0.dp),
                    label = { Text(text = stringResource(id = R.string.account_label)) },
                    value = account,
                    onValueChange = { account = it }
                )

                OutlinedTextField(
                    modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 0.dp),
                    label = { Text(text = stringResource(id = R.string.passwd_label)) },
                    value = passwd,
                    onValueChange = { passwd = it }
                )

                Button(
                    modifier = Modifier.padding(5.dp),
                    content = { Text(text = "登 陆") },
                    onClick = {
                        if (requestHash.isNotEmpty()) {
                            scope.launch {
                                viewModel.channel.send(
                                    MainEvent.LoginAccount(
                                        account = account.text,
                                        passwd = passwd.text,
                                        requestHash = requestHash,
                                        captcha = ""
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun Content(
    navControllerScreen: NavHostController,
    requestHash: String,
    viewModel: MainViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var account by remember {
        mutableStateOf(TextFieldValue())
    }
    var passwd by remember {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.loginState.collect {
            when (it) {
                is LoginState.Error -> {
                    it.error.showToast()
                }

                is LoginState.Idle -> {}
                is LoginState.LoggingIn -> {
                    Toast.makeText(context, "登陆中，请稍后", Toast.LENGTH_SHORT).show()
                }

                is LoginState.Success -> {
                    if (it.loginEntity.status == 1) {
                        viewModel.sendIntent(MainEvent.GetLoginInfo)
                        viewModel.sendIntent(MainEvent.GetProfile(it.loginEntity.sESSION.uid))
                        navControllerScreen.popBackStack()
                    }
                }
            }
        }
    })

    OutlinedTextField(
        modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 0.dp),
        label = { Text(text = stringResource(id = R.string.account_label)) },
        value = account,
        onValueChange = { account = it }
    )

    OutlinedTextField(
        modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 0.dp),
        label = { Text(text = stringResource(id = R.string.passwd_label)) },
        value = passwd,
        onValueChange = { passwd = it }
    )

    Button(
        modifier = Modifier.padding(5.dp),
        content = { Text(text = "登 陆") },
        onClick = {
            if (requestHash.isNotEmpty()) {
                scope.launch {
                    viewModel.channel.send(
                        MainEvent.LoginAccount(
                            account = account.text,
                            passwd = passwd.text,
                            requestHash = requestHash,
                            captcha = ""
                        )
                    )
                }
            }
        }
    )
}