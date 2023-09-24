package com.anpe.coolbbsyou.ui.host.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FullImageScreen(
    navControllerScreen: NavHostController,
    viewModel: MainViewModel
) {
    val list by viewModel.picArray.collectAsState()

    var animateState by remember {
        mutableStateOf(true)
    }

    AnimatedVisibility(
        visible = animateState,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface {
            val state = rememberPagerState(initialPage = list.initialCount) {
                list.picArray.size
            }

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                state = state
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableNoRipple {
//                            animateState = false
                            navControllerScreen.popBackStack()
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(list.picArray[it])
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                )
            }

            BackHandler {
                animateState = false
                navControllerScreen.popBackStack()
            }
        }
    }
}