package com.example.mvi_arquitecture.data.network.dto
import kotlinx.serialization.Serializable
@Serializable
data class PostsUser(
    val userId: Int = 1,
    val id: Int = 1,
    val title: String = "",
    val body: String = ""
)