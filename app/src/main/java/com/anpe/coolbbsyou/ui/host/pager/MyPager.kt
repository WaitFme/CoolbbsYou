package com.anpe.coolbbsyou.ui.host.pager

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.anpe.coolbbsyou.R
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.ui.main.MainViewModel
import com.anpe.coolbbsyou.ui.view.CustomProgress
import com.anpe.coolbbsyou.util.SharedPreferencesUtils
import com.anpe.coolbbsyou.util.Utils.Companion.clickableNoRipple

@Composable
fun MyPager(
    navControllerScreen: NavHostController,
    navControllerPager: NavHostController,
    viewModel: MainViewModel
) {
    val globalState by viewModel.globalState.collectAsState()

    if (globalState.isLogin) {
        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        val configSp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            val (
                avatarRef,
                usernameRef,
                userLevelRef,
                experienceRef,
                experienceSeekRef,
                funRef,
                spacerRef,
                itemRef
            ) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickableNoRipple {
//                        onLogin()
                    }
                    .constrainAs(avatarRef) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                    },
                model = ImageRequest.Builder(context)
                    .data(
                        SharedPreferencesUtils.getString(
                            key = "USER_AVATAR",
                            name = Constants.USER_INFO_PREFS
                        )
                            ?: R.drawable.ic_user_background
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "avatar"
            )

            Text(
                modifier = Modifier
                    .constrainAs(usernameRef) {
                        start.linkTo(avatarRef.end, 10.dp)
                        top.linkTo(avatarRef.top)
                    },
                text = SharedPreferencesUtils.getString(
                    key = "USER_NAME",
                    defValue = "游客",
                    name = Constants.USER_INFO_PREFS
                )!!,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier
                    .constrainAs(userLevelRef) {
                        start.linkTo(avatarRef.end, 10.dp)
                        top.linkTo(usernameRef.bottom, 5.dp)
                    },
                text = "Lv.${
                    SharedPreferencesUtils.getInt(
                        key = "LEVEL",
                        defValue = -1,
                        name = Constants.USER_INFO_PREFS
                    )
                }", // level
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier
                    .constrainAs(experienceRef) {
                        start.linkTo(userLevelRef.end, 25.dp)
                        top.linkTo(userLevelRef.top)
                    },
                text = "${
                    SharedPreferencesUtils.getInt(
                        key = "EXPERIENCE",
                        defValue = 0,
                        name = Constants.USER_INFO_PREFS
                    )
                }/${
                    SharedPreferencesUtils.getInt(
                        key = "NEXT_LEVEL_EXPERIENCE",
                        defValue = 100,
                        name = Constants.USER_INFO_PREFS
                    )
                }",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            CustomProgress(
                modifier = Modifier
                    .constrainAs(experienceSeekRef) {
                        start.linkTo(avatarRef.end, 10.dp)
                        top.linkTo(userLevelRef.bottom, 5.dp)
                        end.linkTo(experienceRef.end)
                        width = Dimension.fillToConstraints
                    },
                currentValue = SharedPreferencesUtils.getInt(
                    key = "EXPERIENCE",
                    defValue = 0,
                    name = Constants.USER_INFO_PREFS
                ),
                strokeWidth = 10f,
                maxValue = SharedPreferencesUtils.getInt(
                    key = "NEXT_LEVEL_EXPERIENCE",
                    defValue = 100,
                    name = Constants.USER_INFO_PREFS
                ),
                primaryColor = MaterialTheme.colorScheme.primary,
                secondaryColor = MaterialTheme.colorScheme.primaryContainer
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(funRef) {
                        start.linkTo(parent.start)
                        top.linkTo(experienceSeekRef.bottom, 20.dp)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val follow = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(
                            SharedPreferencesUtils.getInt(
                                name = Constants.USER_INFO_PREFS,
                                key = "FOLLOW",
                                defValue = -1
                            )
                                .toString()
                        )
                    }
                    append("\n${stringResource(id = R.string.follow)}")
                }
                val fans = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(
                            SharedPreferencesUtils.getInt(
                                name = Constants.USER_INFO_PREFS,
                                key = "FANS",
                                defValue = -1
                            )
                                .toString()
                        )
                    }
                    append("\n${stringResource(id = R.string.fans)}")
                }
                val feed = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(
                            SharedPreferencesUtils.getInt(
                                name = Constants.USER_INFO_PREFS,
                                key = "FEED",
                                defValue = -1
                            )
                                .toString()
                        )
                    }
                    append("\n${stringResource(id = R.string.feed)}")
                }

                Text(
                    modifier = Modifier.weight(1f),
                    text = follow,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = fans,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = feed,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .constrainAs(spacerRef) {
                        top.linkTo(funRef.bottom, 15.dp)
                    }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(itemRef) {
                        top.linkTo(spacerRef.bottom, 0.dp)
                        bottom.linkTo(parent.bottom, 0.dp)
                    }
            ) {
                IconText(text = "我的常去", icon = R.drawable.baseline_hdr_strong_24)
                IconText(text = "我的话题", icon = R.drawable.baseline_label_24)
                IconText(text = "浏览历史", icon = R.drawable.baseline_history_24)
                IconText(text = "我的收藏", icon = R.drawable.baseline_star_24)
                IconText(text = "关于", icon = R.drawable.baseline_error_24)
                IconText(text = "设置", icon = R.drawable.baseline_settings_24)
                IconText(
                    modifier = Modifier.clickableNoRipple {
//                            onLogout()
                    },
                    text = "退出登陆",
                    icon = R.drawable.baseline_exit_to_app_24
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {  }
            ) {
                Text(text = "点击登陆")
            }
        }
    }
}

@Composable
private fun IconText(modifier: Modifier = Modifier, text: String, icon: Int? = null) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .size(20.dp),
                painter = painterResource(id = icon),
                contentDescription = "icon"
            )
        }

        Text(
            modifier = Modifier
                .padding(start = if (icon == null) 30.dp else 15.dp),
            text = text, fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}