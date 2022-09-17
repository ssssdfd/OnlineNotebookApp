package com.example.onlinenotebookapp.repository

import com.example.onlinenotebookapp.MyApi
import com.example.onlinenotebookapp.model.getAllUsers.GetAllUsersResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository (private val api:MyApi) {
    fun createUser(nickname:String, email:String, password:String) = api.createUser(nickname, email, password)

    fun getUser(email: String,password: String) = api.getUser(email, password)

    suspend fun deleteUser(email: String, password: String) = api.deleteUser(email, password)

    fun createRoom(email: String, roomName:String) = api.createRoom(email, roomName)

    fun getRooms(userId:Int) = api.getRooms(userId)

     fun enterRoom(roomId:Int, userId: Int) = api.enterRoom(roomId, userId)

    fun createNote(note:String, roomId:Int, userId: Int) = api.createNote(note, roomId, userId)

    fun getNotesFromRoom(roomId: Int) = api.getNotesFromRoom(roomId)

    fun updateNote(noteId:Int, content:String) = api.updateNote(noteId, content)

    fun getAllUsers() = api.getAllUsers()

    fun deleteRoom(roomId:Int, userId: Int) = api.deleteRoom(roomId, userId)

    fun deleteNote(noteId: Int) = api.deleteNote(noteId)

    fun exitFromRoom(roomId:Int, userId: Int) = api.exitFromRoom(roomId, userId)
}