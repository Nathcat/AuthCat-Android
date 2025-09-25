package com.nathcat.authcat_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nathcat.authcat_android.ui.theme.AuthCatTheme
import com.nathcat.authcat_android.ui.theme.primaryColor
import net.nathcat.authcat.AuthCat
import org.json.simple.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthCatTheme {
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

                            Image(
                                painter = painterResource(id = R.drawable.authcat),
                                contentDescription = "AuthCat logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.CenterHorizontally)
                            )

                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = "AuthCat",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            var username by remember { mutableStateOf(TextFieldValue("")) }
                            var password by remember { mutableStateOf(TextFieldValue("")) }
                            var passwordFocus = remember { FocusRequester() }

                            TextField(
                                value = username,
                                onValueChange = { username = it },
                                label = { Text("Username") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Password),
                                keyboardActions = KeyboardActions(onNext = {
                                    passwordFocus.requestFocus()
                                }),
                                colors = OutlinedTextFieldDefaults.colors( unfocusedContainerColor = primaryColor ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(PaddingValues(vertical = 10.dp))
                            )

                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Password") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Password),
                                keyboardActions = KeyboardActions(onNext = {
                                    val t = Thread {
                                        val authcat = AuthCat(OkHttpProvider())

                                        if (username.text == "" || password.text == "") {
                                            this@LoginActivity.runOnUiThread {
                                                Toast.makeText(
                                                    this@LoginActivity,
                                                    "All fields must be filled out!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                            return@Thread
                                        }

                                        val u = JSONObject()
                                        u.put("username", username.text)
                                        u.put("password", password.text)
                                        val r = authcat.createAuthToken(u)

                                        if (r == null) {
                                            this@LoginActivity.runOnUiThread {
                                                Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                                            }

                                            return@Thread
                                        }
                                        else {
                                            val pw = PrintWriter(FileOutputStream(File(filesDir, "auth.txt")))
                                            pw.write(r)
                                            pw.flush()
                                            pw.close()

                                            onAuthComplete()
                                        }
                                    }

                                    t.isDaemon = true
                                    t.start()
                                }),
                                colors = OutlinedTextFieldDefaults.colors( unfocusedContainerColor = primaryColor ),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(PaddingValues(vertical = 10.dp))
                                    .focusRequester(passwordFocus)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onAuthComplete() {
        val intent = Intent(this@LoginActivity, LoadActivity::class.java)

        startActivity(intent)
    }
}