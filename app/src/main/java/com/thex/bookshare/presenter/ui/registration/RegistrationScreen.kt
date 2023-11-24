package com.thex.bookshare.presenter.ui.registration

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.thex.bookshare.common.widgets.CustomOutlineTextField
import com.thex.bookshare.common.widgets.CustomOutlinedButton

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
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
            CustomOutlineTextField(
                modifier = Modifier.fillMaxWidth(0.9f),
                inputWrapper = confirmPassword,
                onTextValueChange = viewModel::onConfirmPasswordInputChanged,
                label = Strings.CONFIRM_PASSWORD
            )
            MediumSpacer()
            Text(
                text = Strings.FORGOT_PASSWORD,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
            LargeSpacer()
            CustomOutlinedButton(
                buttonName = Strings.SUBMIT,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                viewModel.onTriggerEvent(RegistrationEvent.SubmitEvent)
            }
            Spacer(modifier = Modifier.weight(2f))

        }
    }
}

