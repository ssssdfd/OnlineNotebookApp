package com.example.onlinenotebookapp.model.notes

import com.example.onlinenotebookapp.model.Creator

data class GetNotesFromRoomItem(
    val changed: Boolean,
    val content: String,
    val creationDate: String,
    val creator: Creator,
    val id: Int
)