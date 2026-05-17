package org.example.project.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.example.project.model.User
import org.example.project.model.UserList

class ApiService {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }

    suspend fun fetchData(): String {
        var result = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "dummyjson.com"
                path("test")
            }
        }

        return if (result.status.isSuccess()) {
            result.bodyAsText()
        } else result.status.description
    }

    suspend fun searchUsers(q: String): List<User> {
        val response = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "dummyjson.com"
                path("users/search")
                parameter("q", q)
            }
        }
        if (response.status.isSuccess()) {
            val responseJson = Json.parseToJsonElement(response.bodyAsText())
            val usersJson = responseJson.jsonObject["users"] ?: throw IllegalArgumentException("Invalid 'users' key")
            return Json.decodeFromJsonElement<List<User>>(usersJson)
        } else {
            return emptyList()
        }
    }

    suspend fun queryUsers(limit: Int = 10, skip: Int = 0, searchText: String): UserList {
        val response = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "dummyjson.com"
                path("users/search")
                parameter("limit", limit)
                parameter("skip", skip)
                parameter("q", searchText)
            }
        }

        if (response.status.isSuccess()) {
            val responseJson = Json.parseToJsonElement(response.bodyAsText())
            return Json.decodeFromJsonElement<UserList>(responseJson)
        } else {
            return UserList(emptyList(), 0, 0, 0)
        }
    }

    suspend fun getUser(userId: Long): User? {
        val response = httpClient.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "dummyjson.com"
                path("users/${userId}")
            }
        }
        if (response.status.isSuccess()) {
            val responseJson = Json.parseToJsonElement(response.bodyAsText())
            return Json.decodeFromJsonElement<User>(responseJson)
        } else {
            return null
        }
    }
}