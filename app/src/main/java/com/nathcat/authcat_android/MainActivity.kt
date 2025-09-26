package com.nathcat.authcat_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nathcat.authcat_android.components.NavDrawer
import com.nathcat.authcat_android.components.ProfilePicture
import com.nathcat.authcat_android.ui.theme.AuthCatTheme
import com.nathcat.authcat_android.ui.theme.primaryColor
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user: JSONObject = JSONParser().parse(intent.extras?.getString("user")) as JSONObject

        Toast.makeText(this, "Swipe right for the menu!", Toast.LENGTH_SHORT).show()

        setContent {
            AuthCatTheme {
                NavDrawer(this@MainActivity, user) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = primaryColor
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                ProfilePicture(
                                    url = "https://cdn.nathcat.net/pfps/" + user["pfpPath"],
                                    size = 200.dp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = "Welcome, " + user["fullName"],
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(PaddingValues(top = 20.dp, bottom = 50.dp))
                                )

                                HorizontalDivider()

                                Column(
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(PaddingValues(top = 12.5.dp, bottom = 12.5.dp, start = 5.dp, end = 5.dp))
                                    ) {
                                        Text(
                                            style = MaterialTheme.typography.titleMedium,
                                            text = "Username: ",
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                        )

                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        )

                                        Text(
                                            style = MaterialTheme.typography.titleSmall,
                                            text = user["username"] as String,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(PaddingValues(top = 12.5.dp, bottom = 12.5.dp, start = 5.dp, end = 5.dp))
                                    ) {
                                        Text(
                                            style = MaterialTheme.typography.titleMedium,
                                            text = "Email: ",
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                        )

                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        )

                                        Text(
                                            style = MaterialTheme.typography.titleSmall,
                                            text = user["email"] as String,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}