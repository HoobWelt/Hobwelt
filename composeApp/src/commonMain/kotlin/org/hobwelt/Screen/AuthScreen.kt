/*
* Created on 05 06 2025
*
* @author Yogita
*/

package org.hobwelt.Screen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hobwelt.composeapp.generated.resources.Res
import hobwelt.composeapp.generated.resources.logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource


///
/// AuthScreen
/// to sign in and signup users flow
///

@Composable
fun AuthScreen() {
    var selectedTab by remember { mutableStateOf("Sign In") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val tabs = listOf("Sign In", "Sign Up")

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    fun validate(): Boolean {
        var isValid = true

        emailError = if (email.isBlank() ||
            Regex(
                "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.+)"
            ).matches(email)
        ) {
            isValid = false
            "Invalid email"
        } else ""

        passwordError = if (password.length < 6) {
            isValid = false
            "Password must be at least 6 characters"
        } else ""

        confirmPasswordError = if (selectedTab == "Sign Up" && confirmPassword != password) {
            isValid = false
            "Passwords do not match"
        } else ""

        return isValid
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF4F6F8)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color(0xFFDADCE0), CircleShape)
                    .shadow(5.dp, CircleShape)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (selectedTab == "Sign In") "Welcome Back" else "Create Account",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color(0xFF1A1B1F)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Please ${selectedTab.lowercase()} to continue",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF5F6368),
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            TabSwitcher(tabs, selectedTab) {
                selectedTab = it
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                AuthTextField("Email", email, emailError) { email = it }
                AuthTextField("Password", password, passwordError, true) { password = it }

                if (selectedTab == "Sign Up") {
                    AuthTextField("Confirm Password", confirmPassword, confirmPasswordError, true) {
                        confirmPassword = it
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            SolidButton(
                text = selectedTab,
                enabled = email.isNotBlank() && password.isNotBlank() &&
                        (selectedTab == "Sign In" || confirmPassword.isNotBlank())
            ) {
                if (validate()) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "${selectedTab} successful!",
                            duration = SnackbarDuration.Short
                        )
                    }
                    // Handle Auth Logic here
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Please fix the errors above.",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Forgot Password?",
                color = Color(0xFF5F6368),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable { /* Forgot password action */ }
            )
        }
    }
}

@Composable
fun TabSwitcher(tabs: List<String>, selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEDEEF0))
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach { tab ->
            val selected = selectedTab == tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(tab) }
                    .background(if (selected) Color.White else Color(0xFFf8f2e9))
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = if (selected) Color(0xFF202124) else Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
fun AuthTextField(
    label: String,
    value: String,
    error: String = "",
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = label, color = Color(0xFF9AA0A6)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                when {
                    isPassword -> {
                        ///
                        /// Modify icon issue
                        ///
                        /* IconButton(onClick = { passwordVisible = !passwordVisible }) {
                             Icon(
                                 imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                 contentDescription = if (passwordVisible) "Hide password" else "Show password"
                             )
                         }*/
                    }

                    error.isNotEmpty() -> {
                        ///
                        /// Modify icon issue
                        ///
                        /*Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = Color.Red,
                            modifier = Modifier.size(20.dp)
                        )*/
                    }
                }
            },
            isError = error.isNotEmpty(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF9FAFB),
                focusedContainerColor = Color(0xFFF9FAFB),
                unfocusedIndicatorColor = if (error.isEmpty()) Color(0xFFE0E0E0) else Color.Red,
                focusedIndicatorColor = if (error.isEmpty()) Color(0xFF4285F4) else Color.Red,
                errorIndicatorColor = Color.Red
            )
        )
        AnimatedVisibility(
            visible = error.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun SolidButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(
                1.dp,
                if (enabled) Color(0xFFBDBDBD) else Color(0xFFB0B0B0),
                RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Color(0xFFf8f2e9) else Color(0xFFE0E0E0),
            contentColor = Color.Black
        )
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

