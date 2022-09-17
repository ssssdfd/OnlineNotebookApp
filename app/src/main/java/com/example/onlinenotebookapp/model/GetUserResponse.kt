package com.example.chatapp.model

data class GetUserResponse(
    val email: String,
    val id: Int,
    val nickName: String,
    val registrationDate: String,
    val rooms: List<Any>
)