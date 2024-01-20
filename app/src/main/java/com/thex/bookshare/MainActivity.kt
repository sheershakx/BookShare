package com.thex.bookshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.thex.bookshare.presenter.theme.BookShareTheme
import com.thex.bookshare.presenter.ui.registration.RegistrationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookShareTheme {
                Surface {
                    RegistrationScreen()
                }
            }
        }
    }
}
