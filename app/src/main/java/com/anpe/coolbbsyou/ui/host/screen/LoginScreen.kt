package com.anpe.coolbbsyou.ui.host.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.intent.state.LoginState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToast
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToastString
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navControllerScreen: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navControllerScreen.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { pv ->
            val context = LocalContext.current

            val scope = rememberCoroutineScope()

            val loginState by viewModel.loginState.collectAsState()

            val globalState by viewModel.globalState.collectAsState()

            ConstraintLayout(
                modifier = Modifier
                    .padding(pv)
                    .fillMaxWidth(),
            ) {
                val (titleTXT, descTXT, accountTF, passwdTF, singInBtn) = createRefs()

                var account by remember {
                    mutableStateOf(TextFieldValue())
                }
                var passwd by remember {
                    mutableStateOf(TextFieldValue())
                }
                var showPasswd by remember {
                    mutableStateOf(false)
                }

                Text(
                    modifier = Modifier.constrainAs(titleTXT) {
                        start.linkTo(parent.start, 15.dp)
                        top.linkTo(parent.top, 50.dp)
                    },
                    text = "WelCome!",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.constrainAs(descTXT) {
                        start.linkTo(parent.start, 15.dp)
                        top.linkTo(titleTXT.bottom, 5.dp)
                    },
                    text = "Sign in to CoolApk",
                    fontSize = 25.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(500.dp)
                        .padding(15.dp, 40.dp, 15.dp, 0.dp)
                        .constrainAs(accountTF) {
                            start.linkTo(parent.start)
                            top.linkTo(descTXT.bottom)
                            end.linkTo(parent.end)
                        },
                    label = { Text(text = stringResource(id = R.string.account_label)) },
                    value = account,
                    onValueChange = { account = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = account.text.isNotEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(onClick = { account = TextFieldValue(text = "") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "clear"
                                )
                            }
                        }
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(500.dp)
                        .padding(15.dp, 5.dp, 15.dp, 0.dp)
                        .constrainAs(passwdTF) {
                            start.linkTo(parent.start)
                            top.linkTo(accountTF.bottom)
                            end.linkTo(parent.end)
                        },
                    label = { Text(text = stringResource(id = R.string.passwd_label)) },
                    value = passwd,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (showPasswd) VisualTransformation.None else
                        PasswordVisualTransformation('・'),
                    singleLine = true,
                    trailingIcon = {
                        Row {
                            AnimatedVisibility(
                                visible = passwd.text.isNotEmpty(),
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(onClick = { passwd = TextFieldValue(text = "") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "clear"
                                    )
                                }
                            }

                            IconButton(onClick = { showPasswd = !showPasswd }) {
                                Icon(
                                    imageVector = if (showPasswd) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "clear"
                                )
                            }
                        }
                    },
                    onValueChange = { passwd = it },
                    keyboardActions = KeyboardActions(onDone = {
                        scope.launch {
                            if (account.text.isEmpty() || passwd.text.isEmpty()) {
                                context.showToastString("请输入完整账号密码！")
                                return@launch
                            }
                            val requestHash = globalState.requestHash

                            if (requestHash.isNotEmpty()) {
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
                    })
                )

                Icon(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(15.dp)
                        .clickableNoRipple {
                            scope.launch {
                                if (account.text.isEmpty() || passwd.text.isEmpty()) {
                                    context.showToastString("请输入完整账号密码！")
                                    return@launch
                                }
                                val requestHash = globalState.requestHash

                                if (requestHash.isNotEmpty()) {
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
                        .constrainAs(singInBtn) {
                            top.linkTo(passwdTF.bottom, 15.dp)
                            end.linkTo(parent.end, 15.dp)
                        },
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Sing in"
                )
            }

            LaunchedEffect(key1 = loginState, block = {
                val configSp = context.getSharedPreferences(Constants.CONFIG_PREFS, 0)
                viewModel.loginState.collect {
                    when (it) {
                        is LoginState.Idle -> {
                            viewModel.getRequestHash()
                        }

                        is LoginState.LoggingIn -> {
                            context.showToastString("登陆中，请稍后...")
                        }

                        is LoginState.Success -> {
                            if (it.loginModel.status == 1) {
                                configSp.edit().putBoolean("LOGIN_STATUS", true).apply()
                                viewModel.channel.send(MainEvent.GetProfile(it.loginModel.sESSION.uid))
                                navControllerScreen.popBackStack()
                                context.showToastString("登陆成功！")
                            } else {
                                context.showToastString("账号或密码错误！${it.loginModel.status}")
                            }
                        }

                        is LoginState.Error -> {
                            "登陆失败: ${it.error}".showToast(context)
                        }
                    }
                }
            })
        }
    )
}