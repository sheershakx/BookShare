package com.thex.bookshare.presenter.ui.registration


data class RegistrationState(
    val isInLoginState: Boolean = true,
)

sealed class RegistrationEvent() {
    data class SubmitEvent(val isInLoginState: Boolean) : RegistrationEvent()
    data class SwapRegistrationScreen(val isInLoginState: Boolean) : RegistrationEvent()
}