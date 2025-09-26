package com.nathcat.authcat_android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nathcat.authcat_android.R
import com.nathcat.authcat_android.ui.theme.gradientEnd
import com.nathcat.authcat_android.ui.theme.gradientStart
import kotlinx.coroutines.Dispatchers

@Composable
fun ProfilePicture(url: String, size: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Brush.verticalGradient(listOf(gradientStart, gradientEnd)), shape = CircleShape)
            .clip(CircleShape)
            .size(size)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(url)
                .placeholder(R.drawable.cat)
                .dispatcher(Dispatchers.IO)
                .build(),
            contentDescription = "User's profile picture",
            modifier = Modifier
                .clip(CircleShape)
                .size(size.times(0.95f))
        )
    }
}