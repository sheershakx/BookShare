package com.thex.bookshare.presenter.ui.registration


class RegistrationState(){}

sealed class RegistrationEvent() {
    data object SubmitEvent : RegistrationEvent()
}