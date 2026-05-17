package org.example.project.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.api.ApiService
import org.example.project.model.User

class UserDetailsScreen(val userId: Long, val onGoBackClicked: () -> Unit) : Screen {

    @Composable
    override fun Content() {
        val apiService = ApiService()
        var user by remember { mutableStateOf<User?>(null) }
        MaterialTheme{
            LaunchedEffect(Unit) {
                user = apiService.getUser(userId = userId)
            }

            user?.let { currentUser ->
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Id") },
                        value = currentUser.id.toString(),
                        onValueChange = {},
                        readOnly = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "UserName") },
                        value = currentUser.username,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "First Name") },
                        value = currentUser.firstName,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Last Name") },
                        value = currentUser.lastName,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Email") },
                        value = currentUser.email,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Phone") },
                        value = currentUser.phone,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Gender") },
                        value = currentUser.gender,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Age") },
                        value = currentUser.age.toString(),
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onGoBackClicked()
                        }
                    ) {
                        Text(text = "Go Back")
                    }
                }
            }
        }
    }
}