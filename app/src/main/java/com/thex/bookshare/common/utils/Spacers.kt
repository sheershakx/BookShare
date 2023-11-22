package com.thex.bookshare.common.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SmallSpacer() =
    Spacer(modifier = Modifier.height(8.dp))


@Composable
fun MediumSpacer() =
    Spacer(modifier = Modifier.height(24.dp))

@Composable
fun LargeSpacer() =
    Spacer(modifier = Modifier.height(38.dp))