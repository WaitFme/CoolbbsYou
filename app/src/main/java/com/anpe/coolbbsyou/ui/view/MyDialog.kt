package com.anpe.bilibiliandyou.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun MyDialog(
    title: String,
    onDismissRequest: () -> Unit,
    dismissButton: @Composable () -> Unit,
    deleteButton: (@Composable () -> Unit)?,
    updateButton: (@Composable () -> Unit)?,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    Text(
                        text = title,
                        fontSize = 24.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    content()
                }

                Row(
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .fillMaxWidth()
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    deleteButton?.let {
                        Box {
                            deleteButton()
                        }
                    }
                    Box(modifier = Modifier.padding(start = 5.dp)) {
                        dismissButton()
                    }
                    updateButton?.let {
                        Box(modifier = Modifier.padding(start = 5.dp)) {
                            updateButton()
                        }
                    }
                }
            }
        }
    }
}