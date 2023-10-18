package com.anpe.coolbbsyou.ui.host.pager

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.anpe.coolbbsyou.constant.Constants
import com.anpe.coolbbsyou.data.local.entity.device.DeviceEntity
import com.anpe.coolbbsyou.intent.event.MainEvent
import com.anpe.coolbbsyou.ui.host.screen.manager.ScreenManager
import com.anpe.coolbbsyou.ui.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MyPager(
    navControllerScreen: NavHostController,
    navControllerPager: NavHostController,
    viewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val configSp = context.getSharedPreferences(Constants.CONFIG_PREFS, Context.MODE_PRIVATE)

    val globalState by viewModel.globalState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var checked by remember {
            mutableStateOf(globalState.isNineGrid)
        }

        SettingsSwitchItem(
            title = "首页图片九宫格",
            tip = "首页图片内容是否开启九宫格排版",
            checked = checked,
            onCheckedChange = {
                checked = it

                scope.launch {
                    viewModel.channel.send(MainEvent.OpenNineGrid(checked))
                }
            }
        )

        SettingsSwitchItem(
            title = "屏蔽首页广告号动态",
            tip = "默认不屏蔽。支持酷安健康发展",
            checked = false,
            onCheckedChange = {
            }
        )

        var isLocal by remember {
            mutableStateOf(configSp.getBoolean("IS_LOCAL_DEVICE", true))
        }
        var isCustom by remember {
            mutableStateOf(false)
        }
        SettingsSwitchItem(
            title = "使用本机型号",
            checked = isLocal,
            onCheckedChange = {
                isLocal = !isLocal
                configSp.edit().putBoolean("IS_LOCAL_DEVICE", isLocal).apply()
            }
        )

        AnimatedVisibility(visible = !isLocal) {
            var manufacturer by remember {
                mutableStateOf(TextFieldValue(text = configSp.getString("C_MANUFACTURER", "")!!))
            }
            var brand by remember {
                mutableStateOf(TextFieldValue(text = configSp.getString("C_BRAND", "")!!))
            }
            var model by remember {
                mutableStateOf(TextFieldValue(text = configSp.getString("C_MODEL", "")!!))
            }

            val device0 = DeviceEntity(
                name = "iPhone 15 PRO MAX",
                manufacturer = "apple",
                brand = "apple",
                model = "iPhone16,2"
            )
            val device1 = DeviceEntity(
                name = "魅族20 PRO",
                manufacturer = "meizu",
                brand = "meizu",
                model = "M391Q"
            )
            val device2 = DeviceEntity(
                name = "三星",
                manufacturer = "samsung",
                brand = "samsung",
                model = "SM-G977N"
            )
            val device3 = DeviceEntity(
                name = "Custom",
                manufacturer = manufacturer.text,
                brand = brand.text,
                model = model.text
            )

            val deviceList = listOf(device0, device1, device2, device3)

            var select by remember {
                mutableIntStateOf(configSp.getInt("DEVICE_SELECT", 0))
            }

            Column {
                SettingsSelectItem(
                    title = "机型选择",
                    selectNum = select,
                    list = deviceList,
                    onClick = {
                        select = it
                        configSp.edit().putInt("DEVICE_SELECT", it).apply()
                        configSp.edit().putString("MANUFACTURER", deviceList[it].manufacturer).apply()
                        configSp.edit().putString("BRAND", deviceList[it].brand).apply()
                        configSp.edit().putString("MODEL", deviceList[it].model).apply()
                    }
                )

                AnimatedVisibility(visible = select == 3) {
                    SettingsItem(title = "自定义机型设置", onClick = {
                        isCustom = true
                    })
                }
            }

            if (isCustom) {
                Dialog(onDismissRequest = { isCustom = false }) {
                    Column(
                        modifier = Modifier
                            .width(400.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(15.dp))
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = manufacturer,
                            label = { Text(text = "manufacturer") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                            onValueChange = {
                                manufacturer = it
                                configSp.edit().putString("C_MANUFACTURER", manufacturer.text).apply()
                                configSp.edit().putString("MANUFACTURER", manufacturer.text).apply()
                            }
                        )
                        OutlinedTextField(
                            value = brand,
                            label = { Text(text = "brand") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                            onValueChange = {
                                brand = it
                                configSp.edit().putString("C_BRAND", brand.text).apply()
                                configSp.edit().putString("BRAND", brand.text).apply()
                            }
                        )
                        OutlinedTextField(
                            value = model,
                            label = { Text(text = "model") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                            onValueChange = {
                                model = it
                                configSp.edit().putString("C_MODEL", model.text).apply()
                                configSp.edit().putString("MODEL", model.text).apply()
                            }
                        )
                    }
                }
            }
        }

        SettingsItem(
            title = "设置",
            onClick = {
                navControllerScreen.navigate(ScreenManager.SettingScreen.route)
            }
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    modifier: Modifier = Modifier,
    title: String,
    tip: String? = null,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Box(
        modifier = modifier
            .clickable {
                onCheckedChange(!checked)
            }
            .fillMaxWidth()
            .padding(15.dp)
            .height(50.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            tip?.apply {
                Text(text = this, fontSize = 14.sp)
            }
        }
        Switch(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
    }
}

@Composable
private fun SettingsSelectItem(
    modifier: Modifier = Modifier,
    selectNum: Int,
    title: String,
    list: List<DeviceEntity>,
    onClick: (Int) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var device by remember {
        mutableStateOf(if (list.isNotEmpty()) list[selectNum].name else "未选择")
    }

    Box(
        modifier = modifier
            .clickable {
                expanded = !expanded
            }
            .fillMaxWidth()
            .padding(15.dp)
            .height(50.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            device.apply {
                Text(text = this, fontSize = 14.sp)
            }
        }

        DropdownMenu(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEachIndexed { index, deviceEntity ->
                DropdownMenuItem(text = { Text(text = deviceEntity.name) }, onClick = {
                    device = deviceEntity.name
                    expanded = false
                    onClick(index)
                })
            }
        }
    }
}

@Composable
private fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    tip: String? = null,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            tip?.apply {
                Text(text = this, fontSize = 14.sp)
            }
        }
    }
}