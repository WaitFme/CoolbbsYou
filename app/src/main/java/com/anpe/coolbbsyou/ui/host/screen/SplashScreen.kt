package com.anpe.coolbbsyou.ui.host.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navControllerScreen: NavController, viewModel: MainViewModel) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
        navControllerScreen.popBackStack()
        navControllerScreen.navigate(ScreenManager.MainScreen.route)
    }

    Splash(alphaAnim.value)
}

@Composable
private fun Splash(alpha: Float) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .alpha(alpha),
            model = ImageRequest.Builder(context)
                .data(R.mipmap.ic_launcher)
                .crossfade(true)
                .build(),
            contentDescription = "logo"
        )
    }
}