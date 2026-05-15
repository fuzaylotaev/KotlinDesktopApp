package org.example.project.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class UserList(
    val users: List<User>,
    val total: Int,
    val skip: Int,
    val limit: Int
)