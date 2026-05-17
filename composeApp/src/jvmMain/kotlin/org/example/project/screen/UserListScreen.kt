package org.example.project.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import org.example.project.api.ApiService
import org.example.project.model.UserList
import kotlin.math.ceil

class UserListScreen(val onUserClick: (userId: Long) -> Unit) : Screen {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        var currentPage by remember { mutableStateOf(0) }
        var userList by remember { mutableStateOf(UserList(emptyList(), 0, 0, 0)) }
        var searchText by remember { mutableStateOf("") }
        val apiService = ApiService()
        MaterialTheme{

            LaunchedEffect(Unit) {
                userList = apiService.queryUsers(limit = 10, skip = 0, searchText = "")
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { newSearchText -> searchText = newSearchText },
                        label = { Text("Search Text") },
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = {
                            scope.launch {
                                userList = apiService.queryUsers(limit = 10, skip = 0, searchText = searchText)
                            }
                        }
                    ) {
                        Text(text = "Search")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                ) {
                    TableCell("Id", weight = 0.05f)
                    TableCell("UserName", weight = 0.2f)
                    TableCell("FirstName", weight = 0.2f)
                    TableCell("LastName", weight = 0.2f)
                    TableCell("Email", weight = 0.35f)
                }

                for (user in userList.users) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onUserClick(user.id) }
                    ) {
                        TableCell(text = user.id.toString(), weight = 0.05f)
                        TableCell(text = user.username, weight = 0.2f)
                        TableCell(text = user.firstName, weight = 0.2f)
                        TableCell(text = user.lastName, weight = 0.2f)
                        TableCell(text = user.email, weight = 0.35f)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    repeat(ceil(userList.total.toDouble() / 10).toInt()) { page ->
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = {
                                if (currentPage != page) {
                                    scope.launch {
                                        userList = apiService.queryUsers(limit = 10, skip = page * 10, searchText = searchText)
                                    }
                                }
                                currentPage = page
                            }
                        ) {
                            Text(text = (page + 1).toString())
                        }
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
}