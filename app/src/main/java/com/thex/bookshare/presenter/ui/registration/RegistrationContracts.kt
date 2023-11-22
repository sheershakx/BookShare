package com.thex.bookshare.presenter.ui.registration


class RegistrationState(){}

sealed class RegistrationEvent() {
    object CheckEmail : RegistrationEvent()
}