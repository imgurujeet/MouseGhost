package com.imgurujeet.mouseghost.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.imgurujeet.mouseghost.R
import com.imgurujeet.mouseghost.WebViewActivity
import com.imgurujeet.mouseghost.core.Constants.version
import com.imgurujeet.mouseghost.core.Prefs
import com.imgurujeet.mouseghost.presentation.navigation.Screen
import com.imgurujeet.mouseghost.presentation.screens.components.SettingCard

@Composable
fun SettingScreen(navHost : NavHostController){

    val context = LocalContext.current
    val prefs = remember { Prefs(context) }
    val analytics = Firebase.analytics

    LaunchedEffect(Unit) {
        analytics.logEvent("setting_screen_opened") {
            param("screen_name", "settingScreen")
        }
    }

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
                    text = "Settings",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .clickable {
                            navHost.popBackStack()
                        }
                )
            }

        },
        content = { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // --- State holders reflecting Prefs ---
                    val vibrationEnabled = remember { mutableStateOf(prefs.vibrationEnabled) }
                    val vibrationDuration = remember { mutableStateOf(prefs.vibrationDuration.toFloat()/ 1000f) }
                    val brightnessInterval = remember { mutableStateOf(prefs.brightnessInterval.toFloat() / 1000f) } // in seconds
                    val brightnessPause = remember { mutableStateOf(prefs.brightnessPause.toFloat() / 1000f) } // in seconds

                    // --- Vibration Enabled Switch ---
                    Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Vibration Enabled")
                        Switch(
                            checked = vibrationEnabled.value,
                            onCheckedChange = {
                                vibrationEnabled.value = it
                                prefs.vibrationEnabled = it
                            }
                        )
                    }

                    // --- Vibration Duration Slider ---
                    Column {
                        Text("Vibration Duration: ${vibrationDuration.value.toInt()} sec")

                        Slider(
                            value = vibrationDuration.value,
                            onValueChange = {
                                val newValue = it.coerceAtMost(brightnessInterval.value - 1f)
                                vibrationDuration.value = newValue
                                prefs.vibrationDuration = (newValue * 1000).toLong() // store ms
                            },
                            valueRange = 1f..60f, // 1s to 120s (2 minutes)

                        )
                    }

                    // --- Auto Pause Interval Slider ---
                    Column {
                        Text("Play Duration Before Pause: ${brightnessInterval.value.toInt()} sec")
                        Slider(
                            value = brightnessInterval.value,
                            onValueChange = {
                                brightnessInterval.value = it
                                prefs.brightnessInterval = (it * 1000).toLong() // convert seconds to ms
                            },
                            valueRange = 1f..60f
                        )
                    }

                    // --- Pause Duration Slider ---
                    Column {
                        Text("Pause Duration (dim time): ${brightnessPause.value.toInt()} sec")
                        Slider(
                            value = brightnessPause.value,
                            onValueChange = {
                                brightnessPause.value = it
                                prefs.brightnessPause = (it * 1000).toLong() // convert seconds to ms
                            },
                            valueRange = 1f..30f
                        )
                    }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = """
                            • Ensure brightness is not set too low.  
                            • Keep your phone screen perfectly clean.   
                            • Vibration is not usually needed.  
                        """.trimIndent(),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(16.dp)
                        )


                    }



                    Column {
                        SettingCard(
                            name = "FAQs",
                            url = "",
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "FAQ Icon",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            onClick = {
                                navHost.navigate(Screen.FAQ.route)
                            }
                        )

                        SettingCard(
                            name = "Help & Support",
                            url = "mailto:Mouseghost@proton.me",
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_mail),
                                    contentDescription = "FAQ Icon",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:Mouseghost@proton.me")
                                }
                                ContextCompat.startActivity(context, intent, null)
                            },
                        )

                        val privacyPolicy = stringResource(id = R.string.privacy_policy)

                        SettingCard(
                            name = "Privacy Policy",
                            url = privacyPolicy,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_privacy_policy),
                                    contentDescription = "FAQ Icon",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            onClick = {
                                val intent = Intent(context, WebViewActivity::class.java).apply {
                                    putExtra("url", privacyPolicy )
                                }
                                context.startActivity(intent)
                            },

                        )

                        SettingCard(
                            name = "Dev Page",
                            url = "https://www.x.com/imgurujeet",
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.x.com/imgurujeet"))
                                ContextCompat.startActivity(context, intent, null)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id =R.drawable.x_social_media_black_icon),
                                    contentDescription = "Instagram Icon",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    modifier = Modifier.size(18.dp
                                    )
                                )
                            }
                        )



                    }

                    Column(
                        modifier = Modifier.fillMaxHeight().fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Version ${version}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Thin),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier
                                .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 8.dp)
                        )
                    }
                }
            }


        }


    )
}