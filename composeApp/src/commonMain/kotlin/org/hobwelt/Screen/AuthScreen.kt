/*
* Created on 05 06 2025
*
* @author Yogita
*/

package org.hobwelt.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


///
/// AuthScreeen
/// to sign in and signup users flow
///
@Composable
fun AuthScreen() {
    val selectedTab = remember { mutableStateOf("Sign In") }
    val tabs = listOf("Sign In", "Sign Up")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo
       /* Image(
            painter = painterResource(id = R.drawable.logo), // ✅ Correct way
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp)
        )*/

        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Text(
            text = "Please ${selectedTab.value.lowercase()} to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFEDEEF1)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabs.forEach { tab ->
                val selected = selectedTab.value == tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTab.value = tab }
                        .background(if (selected) Color.White else Color.Transparent)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        color = if (selected) Color.Black else Color.Gray,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AuthTextField("Email")
            AuthTextField("Password")
            if (selectedTab.value == "Sign Up") {
                AuthTextField("Confirm Password")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = selectedTab.value)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Forgot Password?",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { }
        )
    }
}

@Composable
fun AuthTextField(label: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
        )
    )
}
