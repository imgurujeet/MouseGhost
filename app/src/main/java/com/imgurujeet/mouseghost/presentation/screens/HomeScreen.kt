package com.imgurujeet.mouseghost.presentation.screens

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.imgurujeet.mouseghost.R
import com.imgurujeet.mouseghost.core.Prefs
import kotlin.math.sin
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHost: NavHostController) {
    val context = LocalContext.current
    val prefs = remember { Prefs(context) }
    val density = LocalDensity.current
    val window = (context as Activity).window
    val systemUiController = rememberSystemUiController()

    // --- States ---
    var autoPlay by rememberSaveable { mutableStateOf(false) }
    var isPlaying by rememberSaveable { mutableStateOf(false) }
    var dimmed by rememberSaveable { mutableStateOf(false) }
    var countdown by rememberSaveable { mutableStateOf(0) }
    var imageHeightPx by rememberSaveable { mutableStateOf(0f) }
    val originalBrightness = remember { window.attributes.screenBrightness }
    var playerStarted by rememberSaveable { mutableStateOf(false) }
    val analytics = Firebase.analytics

    LaunchedEffect(Unit) {
        analytics.logEvent("home_screen_opened") {
            param("screen_name", "HomeScreen")
        }
    }


    // Pattern selection
    val patternRes = if (prefs.patternType == "list") R.drawable.list_pattern
    else R.drawable.wave_pattern

    // Vibrator
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun startVibration() {
        if (prefs.vibrationEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        prefs.vibrationDuration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(prefs.vibrationDuration)
            }
        }
    }

    fun stopVibration() {
        vibrator.cancel()
    }

    fun keepScreenOn(enable: Boolean) {
        if (enable) {
            window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    fun restoreOriginalBrightness() {
        val lp = window.attributes
        lp.screenBrightness = if (originalBrightness == -1f) -1f else originalBrightness
        window.attributes = lp
    }

    // Brightness control
    // Adjust brightness when isPlaying changes
//    LaunchedEffect(isPlaying) {
//        if (isPlaying) {
//            val lp = window.attributes
//            lp.screenBrightness = 1f // maximum brightness during play
//            window.attributes = lp
//        } else {
//            val lp = window.attributes
//            lp.screenBrightness = originalBrightness // restore previous brightness
//            window.attributes = lp
//        }
//    }

    // --- Toggle Play ---
    fun togglePlay() {
        autoPlay = !autoPlay
        if (!autoPlay) {
            // ✅ Stop everything on manual pause
            isPlaying = false
            stopVibration()
           // setBrightness(1f)
            dimmed = false
            countdown = 0
        } else {
            // ✅ Trigger vibration when starting
            startVibration()
        }
    }

    // --- Brightness + Playback Loop ---
    fun setBrightness(level: Float) {
        val lp = window.attributes
        lp.screenBrightness = level.coerceIn(0f, 1f) // 0f..1f
        window.attributes = lp
    }


    LaunchedEffect(autoPlay) {
        if (autoPlay) {
            isPlaying = true
            setBrightness(1f) // full brightness while playing
            keepScreenOn(true) //  keep screen awake while playing


            while (autoPlay) {
                // --- Play duration ---
                delay(prefs.brightnessInterval)

                // --- Pause duration ---
                isPlaying = false
                dimmed = true
                setBrightness(0.05f) // very low during pause

                // Countdown while paused
                countdown = (prefs.brightnessPause / 1000).toInt()
                while (countdown > 0 && autoPlay) {
                    delay(1000)
                    countdown--
                }

                // --- Resume ---
                if (autoPlay) {
                    isPlaying = true
                    dimmed = false
                    setBrightness(1f) // full brightness
                    startVibration() // optional
                }
            }
        } else {
            // Auto-play stopped or never started
            isPlaying = false
            dimmed = false
            countdown = 0
            restoreOriginalBrightness() // restore system/device default properly
            stopVibration()
            keepScreenOn(false) // allow screen to sleep normally
        }
    }


    // --- Status bar color ---
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    // --- Infinite Transition for scrolling ---
    val infiniteTransition = rememberInfiniteTransition(label = "image_move")
    val offsetProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )
    val smoothOffset = (sin(offsetProgress * Math.PI).toFloat()) * 800f
    val fixedZoom = 1.6f

    // --- UI ---
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFDDFF00))
                    .statusBarsPadding()
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mouse Ghost",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
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
                // Background patterns
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
                    onClick = { togglePlay() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .size(56.dp)
                        .background(color = Color(0xFFDDFF00), shape = RoundedCornerShape(10.dp))
                ) {
                    Icon(
                        painter = if (autoPlay) painterResource(R.drawable.pause)
                        else painterResource(R.drawable.play),
                        contentDescription = if (autoPlay) "Pause" else "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(48.dp)
                    )
                }

                // Countdown overlay
                if (dimmed && countdown > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "Battery Saving",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "Resuming in ${countdown.toString()}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                        }

                    }
                }
            }
        }
    )
}
