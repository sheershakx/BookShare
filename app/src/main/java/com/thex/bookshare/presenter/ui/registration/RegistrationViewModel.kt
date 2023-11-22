package com.thex.bookshare.presenter.ui.registration

import androidx.lifecycle.SavedStateHandle
import com.thex.framework.base.mvi.BaseViewState
import com.thex.framework.base.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : MviViewModel<BaseViewState<RegistrationState>, RegistrationEvent>() {

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val CONFIRM_PASSWORD = "confirm_password"
    }

    val email = savedStateHandle.getStateFlow(EMAIL, Pair("", ""))
    val password = savedStateHandle.getStateFlow(PASSWORD, Pair("", ""))
    val confirmPassword = savedStateHandle.getStateFlow(CONFIRM_PASSWORD, Pair("", ""))

    override fun onTriggerEvent(eventType: RegistrationEvent) {
        TODO("Not yet implemented")
    }

    fun onEmailInputChanged(input: String) {
        savedStateHandle[EMAIL] = email.value.copy(input,"")
    }

    fun onPasswordInputChanged(input: String) {
        savedStateHandle[PASSWORD] = password.value.copy(input,"")
    }

    fun onConfirmPasswordInputChanged(input: String) {
        savedStateHandle[CONFIRM_PASSWORD] = confirmPassword.value.copy(input,"")
    }
}