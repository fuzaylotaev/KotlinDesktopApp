package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import desktopapp.composeapp.generated.resources.Res
import desktopapp.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.example.project.api.ApiService
import org.example.project.model.User
import org.example.project.model.UserList

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    var apiResponse by remember { mutableStateOf("Waiting...") }
    var userList by remember { mutableStateOf(UserList(emptyList(), 0, 0, 0)) }
    MaterialTheme {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                onClick = {
//                    scope.launch {
//                        val apiService = ApiService()
//                        users = apiService.getUsers()
//                    }
//                }
//            ) {
//                Text(text = "Click Me")
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(text = users.toString())
//        }

        scope.launch {
            val apiService = ApiService()
            userList = apiService.getUsers(limit = 10, skip = 0)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                ) {
                    TableCell("Id", weight = 0.2f)
                    TableCell("Username", weight = 0.2f)
                }
            }

            items(userList.users) { user ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    TableCell(text = user.id.toString(), weight = 0.1f)
                    TableCell(text = user.username, weight = 0.1f)
                }
            }
        }

    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false
) {
    Text(
        text = text,
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .weight(weight)
            .border(1.dp, Color.Black)
            .padding(8.dp)
    )
}