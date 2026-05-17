package org.example.project.common

sealed class Screen {
    data object UserLst : Screen()
    data class UserDetails(val userId: Long): Screen()
}