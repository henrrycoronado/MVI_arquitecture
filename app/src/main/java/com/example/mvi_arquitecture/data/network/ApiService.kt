package com.example.mvi_arquitecture.data.network
import com.example.mvi_arquitecture.data.network.dto.PostsUser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
object ApiService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    suspend fun getActivity(userId: String): PostsUser {
        return try {
            var url: String = "https://jsonplaceholder.typicode.com/posts/" + userId
            client.get( url ).body()
        } catch (e: Exception) {
            PostsUser(title = "Error al cargar: ${e.message}")
        }
    }
}