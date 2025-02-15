package com.tafakkur.diaryapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.tafakkur.diaryapp.R

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    loadingState: Boolean = false,
    primaryText: String = "Sign in with Google",
    secondaryText: String = "Please wait...",
    shape: Shape = Shapes().extraSmall,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderStrokeWidth: Dp = 1.dp,
    icon: Int = R.drawable.google_logo,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClicked: ()->Unit,
) {
    var buttonText by remember {
        mutableStateOf(primaryText)
    }
    LaunchedEffect(key1 = loadingState){
        buttonText = if (loadingState) secondaryText else primaryText
    }

    Surface(
        modifier = modifier.clickable(
            enabled = !loadingState
        ) {onClicked() },
        shape = shape,
        color = backgroundColor,
        border = BorderStroke(width = borderStrokeWidth, color = borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Icon(
                painter = painterResource(id = icon), contentDescription = "Google Logo",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buttonText,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            )
            if (loadingState){
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun GoogleButtonPreview() {
        Column {
            GoogleButton{}
            GoogleButton(loadingState = true){}
        }
}