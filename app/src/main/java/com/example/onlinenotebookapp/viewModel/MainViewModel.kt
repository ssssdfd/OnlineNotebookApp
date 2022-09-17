package com.example.onlinenotebookapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.GetUserResponse
import com.example.chatapp.model.MainResponse
import com.example.onlinenotebookapp.model.getAllUsers.GetAllUsersResponseItem
import com.example.onlinenotebookapp.model.notes.GetNotesFromRoomItem
import com.example.onlinenotebookapp.model.room.GetRoomsFromDbItem
import com.example.onlinenotebookapp.model.room.RoomResponse
import com.example.onlinenotebookapp.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository):ViewModel() {
    val for_createUser = MutableLiveData<MainResponse>()
    val for_getUser = MutableLiveData<GetUserResponse>()
    val for_getRooms = MutableLiveData<List<GetRoomsFromDbItem>>()
    val for_createRoom = MutableLiveData<RoomResponse>()
    val for_getNotesFromRoom = MutableLiveData<List<GetNotesFromRoomItem>>()
    val for_getAllUsers = MutableLiveData<List<GetAllUsersResponseItem>>()
    val errorMessage = MutableLiveData<String>()

    fun createUser(nickname:String, email:String, password:String){
        val response = repository.createUser(nickname, email, password)
        response.enqueue(object :Callback<MainResponse>{
            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                if (response.code()==200){
                    for_createUser.postValue(response.body())
                    Log.d("MyLog","Response code:${response.code()}")
                }
                Log.d("MyLog","Response code:${response.code()}")
            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getUser(email: String,password: String){
        val response = repository.getUser(email, password)
        response.enqueue(object :Callback<GetUserResponse>{
            override fun onResponse(call: Call<GetUserResponse>, response: Response<GetUserResponse>) {
                if (response.code()==200){
                    Log.d("MyLog","Response code:${response.code()}")
                    for_getUser.postValue(response.body())
                }
                Log.d("MyLog","Response code:${response.code()}")
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun deleteUser(email: String,password: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(email, password)
        }
    }

    fun getRooms(userId:Int){
        val response = repository.getRooms(userId)
        response.enqueue(object :Callback<List<GetRoomsFromDbItem>>{
            override fun onResponse(call: Call<List<GetRoomsFromDbItem>>, response: Response<List<GetRoomsFromDbItem>>) {
                if (response.code()==200){
                    Log.d("MyLog","Response list of rooms:${response.body()}")
                    for_getRooms.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<GetRoomsFromDbItem>>, t: Throwable) {
               errorMessage.postValue(t.message)
            }
        })
    }

    fun createRoom(email: String, roomName:String){
        val response = repository.createRoom(email, roomName)
        response.enqueue(object :Callback<RoomResponse>{
            override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                if (response.code()==200){
                    Log.d("MyLog","Create room response:${response.body()}")
                    for_createRoom.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun enterRoom(roomId:Int, userId: Int){
        val response = repository.enterRoom(roomId, userId)
        response.enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MyLog","onResponse:${response.code()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("MyLog","onFailure:${t.message}")
            }
        })
    }

    fun createNote(note:String, roomId:Int, userId: Int){
        val response = repository.createNote(note, roomId, userId)
        response.enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MyLog","onResponse:${response.code()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MyLog","onFailure:${t.message}")
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getNotesFromRoom(roomId: Int){
        val response =repository.getNotesFromRoom(roomId)
        response.enqueue(object :Callback<List<GetNotesFromRoomItem>>{
            override fun onResponse(call: Call<List<GetNotesFromRoomItem>>, response: Response<List<GetNotesFromRoomItem>>) {
                Log.d("MyLog","onResponse:${response.body()}")
                for_getNotesFromRoom.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GetNotesFromRoomItem>>, t: Throwable) {
                Log.d("MyLog","onFailure:${t.message.toString()}")
                errorMessage.postValue(t.message)
            }
        })
    }

    fun updateNote(noteId:Int, content:String){
        val response = repository.updateNote(noteId, content)
        response.enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MyLog","onResponse:${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MyLog","onFailure:${t.message.toString()}")
                errorMessage.postValue(t.message)
            }

        })
    }

    fun getAllUsers(){
        val response = repository.getAllUsers()
        response.enqueue(object :Callback<List<GetAllUsersResponseItem>> {
            override fun onResponse(call: Call<List<GetAllUsersResponseItem>>, response: Response<List<GetAllUsersResponseItem>>) {
                Log.d("MyLog","onResponse:${response.body()}")
                for_getAllUsers.postValue(response.body())
            }

            override fun onFailure(call: Call<List<GetAllUsersResponseItem>>, t: Throwable) {
                Log.d("MyLog","onFailure:${t.message.toString()}")
                errorMessage.postValue(t.message)
            }
        })
    }

    fun deleteRoom(roomId:Int, userId: Int){
        val response = repository.deleteRoom(roomId, userId)
        response.enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MyLog","onResponse:${response.code()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("MyLog","onFailure:${t.message}")
            }
        })
    }

    fun deleteNote(noteId: Int){
        val response = repository.deleteNote(noteId)
        response.enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MyLog","onResponse:${response.code()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("MyLog","onFailure:${t.message}")
            }
        })
    }

    fun exitFromRoom(roomId: Int, userId: Int){
        val response = repository.exitFromRoom(roomId, userId)
        response.enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("MyLog","onResponse:${response.code()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                errorMessage.postValue(t.message)
                Log.d("MyLog","onFailure:${t.message}")
            }
        })
    }
}