package com.thex.bookshare.presenter.ui.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thex.bookshare.R
import com.thex.bookshare.common.utils.LargeSpacer
import com.thex.bookshare.common.utils.MediumSpacer
import com.thex.bookshare.common.utils.SmallSpacer
import com.thex.bookshare.common.utils.Strings
import com.thex.bookshare.common.utils.showToast
import com.thex.bookshare.common.widgets.CustomOutlineTextField
import com.thex.bookshare.common.widgets.CustomOutlinedButton
import com.thex.framework.base.mvi.BaseViewState
import com.thex.framework.extension.cast
import io.github.jan.supabase.exceptions.BadRequestRestException

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val email by viewModel.emailState.collectAsState()
    val password by viewModel.passwordState.collectAsState()
    val confirmPassword by viewModel.confirmPasswordState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var isInLoginState by remember { mutableStateOf(true) }
    val validateLoginFields by viewModel.validateLoginFields().collectAsState()
    val validateSignupFields by viewModel.validateSignupFields().collectAsState()

    LaunchedEffect(key1 = uiState) {
        if (uiState is BaseViewState.Data) {
            val resultState = uiState.cast<BaseViewState.Data<RegistrationState>>().value
            isInLoginState = resultState.isInLoginState
        }
        if (uiState is BaseViewState.Error) {
            val resultState = uiState.cast<BaseViewState.Error>()
            if (resultState.throwable is BadRequestRestException) {
                //user is already registered or invalid login credentials
                //opt for login process instead
                context.showToast(
                    (resultState.throwable as BadRequestRestException).description
                        ?: (resultState.throwable as BadRequestRestException).error
                )
            } else {
                context.showToast(resultState.throwable.message.toString())
            }
        }

    }
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LargeSpacer()
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(240.dp), contentScale = ContentScale.Crop
            )
            LargeSpacer()
            Text(
                text = if (isInLoginState) "Login" else "Sign-up",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomOutlineTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                inputWrapper = email,
                onTextValueChange = viewModel::onEmailInputChanged,
                label = Strings.EMAIL,
            )
            SmallSpacer()
            CustomOutlineTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                inputWrapper = password,
                onTextValueChange = viewModel::onPasswordInputChanged,
                label = Strings.PASSWORD
            )
            SmallSpacer()
            if (!isInLoginState) {
                CustomOutlineTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    inputWrapper = confirmPassword,
                    onTextValueChange = viewModel::onConfirmPasswordInputChanged,
                    label = Strings.CONFIRM_PASSWORD
                )
            }
            MediumSpacer()
            Text(
                text = Strings.FORGOT_PASSWORD,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
            SmallSpacer()
            Text(
                text = Strings.SIGNUP_HYPERLINK,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline
                ), modifier = Modifier.clickable {
                    viewModel.onTriggerEvent(RegistrationEvent.SwapRegistrationScreen(isInLoginState))
                }
            )
            LargeSpacer()
            CustomOutlinedButton(
                enabled = if (isInLoginState) validateLoginFields else validateSignupFields,
                buttonName = if (isInLoginState) Strings.LOGIN else Strings.SIGNUP,
                modifier = Modifier.fillMaxWidth(0.9f),
                isLoading = uiState is BaseViewState.Loading
            ) {
                viewModel.onTriggerEvent(RegistrationEvent.SubmitEvent(isInLoginState))
            }
            Spacer(modifier = Modifier.weight(2f))

        }
    }
}

