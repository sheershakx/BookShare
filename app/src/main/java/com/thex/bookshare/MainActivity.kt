package com.thex.bookshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thex.bookshare.app.BookShareApplication
import com.thex.bookshare.presenter.theme.BookShareTheme
import com.thex.bookshare.presenter.ui.registration.RegistrationScreen
import dagger.hilt.InstallIn
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
