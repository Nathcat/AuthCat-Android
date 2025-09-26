package com.nathcat.authcat_android.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nathcat.authcat_android.R
import com.nathcat.authcat_android.ui.theme.primaryColor
import com.nathcat.authcat_android.ui.theme.secondaryColor
import org.json.simple.JSONObject

@Composable
fun NavDrawer(context: Context, user: JSONObject, content: @Composable () -> Unit) {
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = secondaryColor
        ) {
            Row(
                modifier = Modifier.padding(PaddingValues(top = 50.dp, bottom = 50.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat),
                    contentDescription = "nathcat.net logo",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(PaddingValues(start = 50.dp))
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = user["fullName"] as String
                    )

                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = user["username"] as String
                    )
                }
            }

            HorizontalDivider()

            NavigationDrawerItem(
                label = {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.authcat),
                            contentDescription = "AuthCat logo",
                            modifier = Modifier.size(75.dp)
                        )

                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = "View on nathcat.net",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                        }, selected = false, onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://data.nathcat.net/sso"))
                context.startActivity(intent)
            })
        }
    },
        content = content
    )
}