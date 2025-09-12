package com.imgurujeet.mouseghost.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.imgurujeet.mouseghost.R
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imgurujeet.mouseghost.core.Prefs
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHost: NavHostController) {
    val context= LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var imageHeightPx by remember { mutableStateOf(0f) }
    val prefs = remember { Prefs(context) }
    val density = LocalDensity.current
    val systemUiController = rememberSystemUiController()

    // Pattern type (list or grid)
    val patternRes = if (prefs.patternType == "list") R.drawable.list_pattern
    else R.drawable.wave_pattern

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true // false -> white icons
        )
    }
    val infiniteTransition = rememberInfiniteTransition(label = "image_move")

    // Runs up and down very quickly
    val offsetProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,   // 🚀 0.5 sec per cycle (very fast)
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val smoothOffset = (sin(offsetProgress * Math.PI).toFloat()) * 800f


    val fixedZoom = 1.6f // always zoomed

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFDDFF00),)
                    .statusBarsPadding()
                    .height(56.dp),
                contentAlignment = Alignment.Center,

            ) {
                Text(
                    text = "Mouse Ghost",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(24.dp)
                        .clickable {
                            navHost.navigate("settings")
                        }
                )
            }

        },
        containerColor = Color.White,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = fixedZoom
                            scaleY = fixedZoom
                        }
                ) {
                    val gapPx = with(density) { 16.dp.toPx() }
                    val cycleHeight = imageHeightPx + gapPx
                    val translate = if (isPlaying) -(smoothOffset) else 0f

                    // First image
                    Image(
                        painter = painterResource(id = patternRes),
                        contentDescription = "Pattern 1",
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned {
                                imageHeightPx = it.size.height.toFloat()
                            }
                            .graphicsLayer {
                                translationY = translate
                            },
                        contentScale = ContentScale.Crop
                    )

                    // Second image below first with 16.dp gap
                    Image(
                        painter = painterResource(id = patternRes),
                        contentDescription = "Pattern 2",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                translationY = translate + cycleHeight
                            },
                        contentScale = ContentScale.Crop
                    )
                }

                // Play / Pause button
                IconButton(
                    onClick = { isPlaying = !isPlaying },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .size(56.dp)
                        .background(color = Color(0xFFDDFF00), shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = if (isPlaying) painterResource(R.drawable.pause)
                        else painterResource(R.drawable.play),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    )
}
