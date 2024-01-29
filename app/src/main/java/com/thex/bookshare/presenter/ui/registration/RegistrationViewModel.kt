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
                        setState(BaseViewState.Loading)
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

    private suspend fun observeAuthSessions() {
        supabaseAuth.sessionStatus.collect {
            when (it) {
                is SessionStatus.Authenticated -> {
                    setState(BaseViewState.Empty)
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

    private fun passwordErrorCheck(input: String): String {
        return if (input.isNullOrEmpty()) "Password can't be empty" else ""
    }

    private fun emailErrorCheck(input: String): String {
        return if (input.isNullOrEmpty()) "Email can't be empty" else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                input
            ).matches()
        ) "Invalid email format" else ""
    }

    fun validateLoginFields() =
        combine(emailState, passwordState) { emailState, passwordState ->
            if (emailState.second.isNotEmpty() || passwordState.second.isNotEmpty()) return@combine false
            emailState.first.isNotEmpty() && passwordState.first.isNotEmpty()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), false)

    fun validateSignupFields() =
        combine(
            emailState,
            passwordState,
            confirmPasswordState
        ) { emailState, passwordState, confirmPasswordState ->
            if (emailState.second.isNotEmpty() || passwordState.second.isNotEmpty() || confirmPasswordState.second.isNotEmpty()) return@combine false
            emailState.first.isNotEmpty()
                    && passwordState.first.isNotEmpty()
                    && confirmPasswordState.first.isNotEmpty()
                    && passwordState.first == confirmPasswordState.first
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), false)

    fun onEmailInputChanged(input: String) {
        savedStateHandle[EMAIL] = emailState.value.copy(input, emailErrorCheck(input))
    }

    fun onPasswordInputChanged(input: String) {
        savedStateHandle[PASSWORD] = passwordState.value.copy(input, passwordErrorCheck(input))
    }

    fun onConfirmPasswordInputChanged(input: String) {
        savedStateHandle[CONFIRM_PASSWORD] =
            confirmPasswordState.value.copy(input, passwordErrorCheck(input))
    }


}