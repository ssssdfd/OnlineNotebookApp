package com.example.onlinenotebookapp.model.room

import com.example.onlinenotebookapp.model.Creator

data class RoomResponse(
    val creationDate: String,
    val creator: Creator,
    val id: Int,
    val notes: List<Any>,
    val roomName: String,
    val users: List<User>
)