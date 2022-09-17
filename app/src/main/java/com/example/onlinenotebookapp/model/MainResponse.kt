package com.example.chatapp.model

data class MainResponse(
    val id: Int,
    val nickName: String,
    val email: String,
    val registrationDate: String,
    val rooms: Any
)