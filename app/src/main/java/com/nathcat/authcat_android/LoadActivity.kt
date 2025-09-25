package com.nathcat.authcat_android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nathcat.authcat_android.components.StartupLoading
import com.nathcat.authcat_android.ui.theme.AuthCatTheme
import com.nathcat.authcat_android.ui.theme.primaryColor
import net.nathcat.authcat.AuthCat
import org.json.simple.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Scanner

class LoadActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthCatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primaryColor
                ) {
                    StartupLoading()

                    val authThread = Thread {
                        val authcat = AuthCat(OkHttpProvider())

                        try {
                            val fis = FileInputStream(File(filesDir, "auth.txt"))
                            val s = Scanner(fis)

                            if (s.hasNextLine()) {
                                val token = s.nextLine()
                                println(token)
                                val result = authcat.tokenAuth(token)

                                if (result.result) {
                                    onAuthComplete(result.user)
                                    return@Thread
                                }
                                else {
                                    this@LoadActivity.runOnUiThread {
                                        Toast.makeText(
                                            this@LoadActivity,
                                            "Invalid token, please login again!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    onAuthFail()
                                    return@Thread
                                }
                            }
                            else {
                                this@LoadActivity.runOnUiThread {
                                    Toast.makeText(
                                        this@LoadActivity,
                                        "Please login!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                onAuthFail()
                                return@Thread
                            }
                        }
                        catch (e: IOException) {
                            e.printStackTrace()
                            onAuthFail()
                            return@Thread
                        }
                    }

                    authThread.isDaemon = true
                    authThread.start()
                }
            }
        }
    }

    private fun onAuthComplete(user: JSONObject) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user.toJSONString())

        startActivity(intent)
    }

    private fun onAuthFail() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
