package com.thex.bookshare.presenter.ui.registration

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.thex.framework.base.mvi.BaseViewState
import com.thex.framework.base.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val supabaseAuth: Auth
) : MviViewModel<BaseViewState<RegistrationState>, RegistrationEvent>() {


    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val CONFIRM_PASSWORD = "confirm_password"
    }

    val emailState = savedStateHandle.getStateFlow(EMAIL, Pair("", ""))
    val passwordState = savedStateHandle.getStateFlow(PASSWORD, Pair("", ""))
    val confirmPasswordState = savedStateHandle.getStateFlow(CONFIRM_PASSWORD, Pair("", ""))

    init {
        safeLaunch {
            observeAuthSessions()
        }
    }

    override fun onTriggerEvent(eventType: RegistrationEvent) {
        when (eventType) {
            is RegistrationEvent.SubmitEvent -> {
                if (eventType.isInLoginState) {
                    //proceed for logging in user
                    safeLaunch {
                        supabaseAuth.signInWith(Email) {
                            email = emailState.value.first
                            password = passwordState.value.first
                        }
                    }

                } else {
                    //proceed for signing up user
                    safeLaunch {
                        supabaseAuth.signUpWith(Email) {
                            email = emailState.value.first
                            password = passwordState.value.first
                        }
                    }
                }


            }

            is RegistrationEvent.SwapRegistrationScreen -> {
                setState(BaseViewState.Data(RegistrationState(isInLoginState = !eventType.isInLoginState)))
            }
        }
    }

    fun validateLoginFields() =
        combine(emailState, passwordState) { emailState, passwordState ->
            emailState.first.isNotEmpty() && passwordState.first.isNotEmpty()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), false)

    fun validateSignupFields() =
        combine(
            emailState,
            passwordState,
            confirmPasswordState
        ) { emailState, passwordState, confirmPasswordState ->
            emailState.first.isNotEmpty()
                    && passwordState.first.isNotEmpty()
                    && confirmPasswordState.first.isNotEmpty()
                    && passwordState.first == confirmPasswordState.first
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), false)

    private suspend fun observeAuthSessions() {
        supabaseAuth.sessionStatus.collect {
            when (it) {
                is SessionStatus.Authenticated -> {
                    Timber.i("Authenticated:: ${it.session.user}")
                }

                SessionStatus.LoadingFromStorage -> {
                    Timber.i("Loading from storage")
                }

                SessionStatus.NetworkError -> {
                    Timber.i("Network Error")

                }

                SessionStatus.NotAuthenticated -> {
                    Timber.i("Not authenticated")
                }
            }
        }
    }


    fun onEmailInputChanged(input: String) {
        savedStateHandle[EMAIL] = emailState.value.copy(input, "")
    }

    fun onPasswordInputChanged(input: String) {
        savedStateHandle[PASSWORD] = passwordState.value.copy(input, "")
    }

    fun onConfirmPasswordInputChanged(input: String) {
        savedStateHandle[CONFIRM_PASSWORD] = confirmPasswordState.value.copy(input, "")
    }
}