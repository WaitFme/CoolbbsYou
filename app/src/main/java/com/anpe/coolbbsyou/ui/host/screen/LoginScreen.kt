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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.anpe.coolbbsyou.data.intent.MainIntent
import com.anpe.coolbbsyou.data.remote.repository.RemoteRepository
import com.anpe.coolbbsyou.data.state.LoginState
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.util.LoginUtils.Companion.createRequestHash
import com.anpe.coolbbsyou.util.ToastUtils.Companion.showToast
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navControllerScreen: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "登陆") }, navigationIcon = {
                IconButton(onClick = { navControllerScreen.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        }
    ) {
        Column(Modifier.padding(it).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Content(navControllerScreen, viewModel)
        }
    }
}

@Composable
private fun Content(navControllerScreen: NavHostController, viewModel: MainViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val TAG = "LoginScreen"

    val repository = RemoteRepository()

    var requestHash by remember {
        mutableStateOf("")
    }

    var account by remember {
        mutableStateOf(TextFieldValue())
    }
    var passwd by remember {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(key1 = true, block = {
        repository.getRequestHash().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val body = response.body()?.string()
                body?.apply {
                    requestHash = Jsoup.parse(this).createRequestHash()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

        })

        viewModel.loginState.collect {
            when (it) {
                is LoginState.Error -> {
                    it.error.showToast()
                }
                LoginState.Idle -> {}
                LoginState.LoggingIn -> {
                    Toast.makeText(context, "登陆中，请稍后", Toast.LENGTH_SHORT).show()
                }
                is LoginState.Success -> {
                    if (it.loginEntity.status == 1) {
                        navControllerScreen.popBackStack()
                    }
                }
            }
        }
    })


    OutlinedTextField(
        modifier = Modifier.padding(5.dp),
        label = { Text(text = "Account") },
        value = account,
        onValueChange = { account = it }
    )

    OutlinedTextField(
        modifier = Modifier.padding(5.dp),
        label = { Text(text = "Password") },
        value = passwd,
        onValueChange = { passwd = it }
    )

    Button(onClick = {
        scope.launch {
            if (requestHash.isNotEmpty()) {
                viewModel.channel.send(MainIntent.LoginAccount(account = account.text, passwd = passwd.text, requestHash = requestHash, captcha = ""))
            }
        }
    }) {
        Text(text = "登陆")
    }
}