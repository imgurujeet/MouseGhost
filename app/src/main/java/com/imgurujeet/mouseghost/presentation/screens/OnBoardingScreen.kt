package com.imgurujeet.mouseghost.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.imgurujeet.mouseghost.R
import com.imgurujeet.mouseghost.presentation.navigation.Screen

@Composable
fun OnBoardingScreen(navHost: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = screenHeight * 0.02f) // dynamic top padding
                    .windowInsetsPadding(WindowInsets.systemBars),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mouse Ghost",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = (screenWidth.value * 0.06f).sp, // dynamic font size
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    Text(
                        text = "⚠️ Compatibility Note:",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = (screenWidth.value * 0.05f).sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding((screenWidth * 0.04f))
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = screenWidth * 0.04f)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFF30AD32), shape = RoundedCornerShape(10.dp))
                                .padding((screenWidth * 0.02f)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_compatible_mouse_icon),
                                contentDescription = "optic mouse icon",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                text = "Works With:\nVisible Red Light Mouse",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = (screenWidth.value * 0.035f).sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = screenHeight * 0.01f)
                            )
                        }

                        Spacer(modifier = Modifier.width(screenWidth * 0.02f))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFFF6B6B), shape = RoundedCornerShape(10.dp))
                                .padding((screenWidth * 0.02f)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_incompatible_mouse_icon),
                                contentDescription = "incompatible mouse icon",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                text = "Don't Works With:\nInvisible IR Light Mouse",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = (screenWidth.value * 0.035f).sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = screenHeight * 0.01f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(screenHeight * 0.01f))

                    Text(
                        text = "Note: This app may not work with all mouse models, especially those using infrared (IR) light for tracking. Please test with your specific mouse to ensure compatibility.",
                        color = Color.Red,
                        fontSize = (screenWidth.value * 0.035f).sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding((screenWidth * 0.04f))
                    )

                    Spacer(modifier = Modifier.height(screenHeight * 0.015f))

                    Text(
                        text = "💡 Quick Start Tips",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = (screenWidth.value * 0.045f).sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = screenWidth * 0.04f)
                    )

                    Spacer(modifier = Modifier.height(screenHeight * 0.008f))

                    Text(
                        text = "• Clean your phone screen for better performance.\n• A screen protector might affect tracking — remove if needed.",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = (screenWidth.value * 0.035f).sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = screenWidth * 0.04f)
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( bottom = screenHeight * 0.05f)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navHost.navigate(Screen.Home.route) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDDFF00)),
                ) {
                    Text(
                        text = "Let’s Go \uD83D\uDE80",
                        color = Color.Black,
                    )
                }
            }
        }
    )
}


