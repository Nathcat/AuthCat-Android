package com.nathcat.authcat_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nathcat.authcat_android.ui.theme.AuthCatTheme
import com.nathcat.authcat_android.ui.theme.primaryColor
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user: JSONObject = JSONParser().parse(intent.extras?.getString("user")) as JSONObject

        setContent {
            AuthCatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primaryColor
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = "Welcome, " + user["fullName"],
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}