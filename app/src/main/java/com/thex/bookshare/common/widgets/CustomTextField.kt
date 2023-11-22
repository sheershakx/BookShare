package com.thex.bookshare.common.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thex.bookshare.presenter.theme.Black
import com.thex.bookshare.presenter.theme.Green50
import com.thex.bookshare.presenter.theme.inputFields

@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    inputWrapper: Pair<String, String>,
    onTextValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    inputTextStyle: TextStyle = TextStyle(),
    imeAction: ImeAction = ImeAction.Default,
    isButtonClicked: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLength: Int = 30,
    isSingleLine: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Transparent,
        backgroundColor = Transparent,
    )
    Column {
        CompositionLocalProvider(
            LocalTextSelectionColors provides customTextSelectionColors
        ) {
            TextField(
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused.value = it.isFocused }
                    .border(
                        width = 1.dp,
                        color = getBorderColor(isFocused.value, inputWrapper, isButtonClicked),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(alignment = Alignment.CenterHorizontally),
                value = inputWrapper.first,
                onValueChange = {
                    if (it.length > maxLength) {
                        onTextValueChange(it.take(maxLength))
                    } else
                        onTextValueChange(it)
                },
                label = {
                    Text(
                        text = label,
                        color = getLabelColor(isFocused.value, inputWrapper, isButtonClicked),
                        textAlign = TextAlign.Start,
                        style = getLabelStyle(
                            isFocused = isFocused.value,
                            inputWrapper = inputWrapper
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction, keyboardType = keyboardType
                ),
                keyboardActions = keyboardActions,
                textStyle = inputTextStyle,
                singleLine = isSingleLine,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = inputFields,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = Black
                ),
                visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,

                )

            if (inputWrapper.second != "error") {
                AnimatedVisibility(visible = !inputWrapper.second.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = inputWrapper.second,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }


    }
}

@Composable
fun getBorderColor(
    isFocused: Boolean,
    inputWrapper: Pair<String, String>,
    isButtonClicked: Boolean
): Color {
    return if (!isFocused && !isButtonClicked) {
        MaterialTheme.colorScheme.secondary
    } else if (inputWrapper.second.isEmpty()) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }
}

@Composable
fun getLabelColor(
    isFocused: Boolean,
    inputWrapper: Pair<String, String>,
    isButtonClicked: Boolean
): Color {
    return if (!isFocused && !isButtonClicked) {
        MaterialTheme.colorScheme.secondary
    } else if (inputWrapper.second.isEmpty()) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }
}

@Composable
fun getLabelStyle(
    isFocused: Boolean,
    inputWrapper: Pair<String, String>
): TextStyle {
    return if (isFocused || inputWrapper.first.isNotEmpty()) {
        MaterialTheme.typography.headlineSmall
    } else {
        MaterialTheme.typography.bodyLarge
    }
}




