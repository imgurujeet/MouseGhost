package com.imgurujeet.mouseghost.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FAQScreen(navHost: NavHostController) {
    val faqItems = listOf(
        "How do the settings work?" to """
            • Brightness Level: Controls screen brightness. Higher brightness usually improves detection.
            • Pattern Style: Choose movement/animation style. Some mice respond better to certain styles.
            • Pause Time: Adjusts delay between loops for more natural activity.
            • Stealth Mode: Randomizes movements to avoid detection by monitoring software.
        """.trimIndent(),
        "Which types of mice work best with Mouse Ghost?" to """
            • Works best with optical mice using red light.
            • Budget and mid-range mice usually work fine.
            • Invisible/infrared mice may not always detect patterns.
        """.trimIndent(),
        "Why doesn’t my mouse move when using the app?" to """
            • Set screen brightness high.
            • Clean your screen and mouse sensor.
            • Reposition the mouse flat or at an angle.
            • Remove thick phone cases.
            • Use a red-light mouse for best results.
        """.trimIndent(),
        "My mouse with invisible light (infrared) isn’t working. What can I do?" to """
            • Test with a red-light mouse.
            • Place mouse at a perpendicular angle to phone.
            • Enable Stealth Mode or Vibration Mode if available.
        """.trimIndent(),
        "Will Mouse Ghost drain my battery?" to """
            Yes. Since your screen stays active, it may use more power.
            ⚡ Keep your phone plugged in for long sessions.
        """.trimIndent(),
        "Can I use Mouse Ghost at work?" to """
            Mouse Ghost is for personal use.
            ⚠️ Do not use if it violates your employer’s policies.
        """.trimIndent(),
        "Is Mouse Ghost safe?" to """
            ✔ Does not connect to your computer.
            ✔ Does not collect data.
            ✔ Only displays patterns for the mouse to detect.
        """.trimIndent(),
        "How can I contact support?" to """
            📧 Email: mouseghost@proton.me
        """.trimIndent()
    )

    var expandedIndex by rememberSaveable { mutableStateOf<Int?>(null) }

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
                    text = "❓ Mouse Ghost – FAQ",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)

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

        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(faqItems) { index, (question, answer) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedIndex = if (expandedIndex == index) null else index
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = question,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (expandedIndex == index) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }

                        AnimatedVisibility(visible = expandedIndex == index) {
                            Text(
                                text = answer,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
