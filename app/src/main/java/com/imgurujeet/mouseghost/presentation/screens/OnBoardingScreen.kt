package com.imgurujeet.mouseghost.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.imgurujeet.mouseghost.R
import com.imgurujeet.mouseghost.presentation.navigation.Screen

@Composable
fun OnBoardingScreen(
    navHost: NavHostController
) {
    Scaffold (
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp)
                    .windowInsetsPadding(WindowInsets.systemBars),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Mouse Ghost",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),

            ) {
                Column(
                ) {
                    Text(
                        text = "⚠️ Compatibility Note:",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        modifier=Modifier.padding(16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ){
                        Column(
                            modifier = Modifier.weight(1f)
                                .background(Color(0xFF30AD32), shape = RoundedCornerShape(10.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_compatible_mouse_icon),
                                contentDescription = "optic mouse icon",

                            )
                            Text(
                                text = "Works With:"+
                                        "\nVisible Red Light Mouse",
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier=Modifier.padding(16.dp),
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.weight(0.1f))

                        Column(
                            modifier = Modifier.weight(1f)
                                .background(Color(0xFFFF6B6B), shape = RoundedCornerShape(10.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_incompatible_mouse_icon),
                                contentDescription = "incompatible mouse icon",

                            )
                            Text(
                                text = "Don't Works With:"+
                                        "\nInvisible IR Light Mouse",
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier=Modifier.padding(16.dp),
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Note: This app may not work with all mouse models, especially those using infrared (IR) light for tracking. Please test with your specific mouse to ensure compatibility.",
                        color = Color.Red,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Normal,
                        modifier=Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "💡 Quick Start Tips",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        modifier=Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Clean your phone screen for better performance.\n" +
                                "• A screen protector might affect tracking — remove if needed.",
                                color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Normal,
                        modifier=Modifier.padding(16.dp)
                    )



                }
            }

        },

        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 80.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                contentAlignment = Alignment.Center // centers content horizontally
            ) {
                Button(
                    onClick = {
                        navHost.navigate(Screen.Home.route)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDDFF00)
                    )
                ) {
                    Text(text = "Let’s Go \uD83D\uDE80",
                        color = Color.Black,
                    )
                }
            }
        }

    )
}

