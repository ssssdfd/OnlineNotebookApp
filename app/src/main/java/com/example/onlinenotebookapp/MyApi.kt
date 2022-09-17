package com.example.onlinenotebookapp

import com.example.chatapp.model.GetUserResponse
import com.example.chatapp.model.MainResponse
import com.example.onlinenotebookapp.model.getAllUsers.GetAllUsersResponse
import com.example.onlinenotebookapp.model.getAllUsers.GetAllUsersResponseItem
import com.example.onlinenotebookapp.model.notes.GetNotesFromRoomItem
import com.example.onlinenotebookapp.model.room.GetRoomsFromDb
import com.example.onlinenotebookapp.model.room.GetRoomsFromDbItem
import com.example.onlinenotebookapp.model.room.RoomResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @PUT("api/user/create")
     fun createUser(
        @Query("nickname") nickname: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<MainResponse>

    @GET("api/user/login")
     fun getUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<GetUserResponse>


    @DELETE("api/user/delete")
   suspend fun deleteUser(
        @Query("email") email: String,
        @Query("password") password: String
    )

    @PUT("api/room/create")
    fun createRoom(@Query("email")email: String, @Query("roomName")roomName:String):Call<RoomResponse>

    @GET("api/room/get")
    fun getRooms(@Query("userId")userId:Int):Call<List<GetRoomsFromDbItem>>

    @PUT("api/room/enter/{roomId}")
     fun enterRoom(@Path("roomId")roomId:Int,@Query("userId")userId: Int):Call<String>

     @POST("api/note/create")
     fun createNote(@Query("note")note:String, @Query("roomId")roomId:Int, @Query("userId")userId: Int):Call<String>

     @GET("api/room/get/notes")
     fun getNotesFromRoom(@Query("roomId")roomId: Int):Call<List<GetNotesFromRoomItem>>

     @POST("api/note/update")
     fun updateNote(@Query("noteId")noteId:Int, @Query("content")content:String):Call<String>

     @GET("api/user/get-all")
     fun getAllUsers():Call<List<GetAllUsersResponseItem>>

    @DELETE("api/room/delete/{roomId}")
    fun deleteRoom(@Path("roomId")roomId:Int,@Query("userId")userId: Int):Call<String>

    @DELETE("api/note/delete")
    fun deleteNote(@Query("noteId")noteId:Int):Call<String>

    @DELETE("api/room/exit")
    fun exitFromRoom(@Query("roomId")roomId:Int,@Query("userId")userId: Int):Call<String>

    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
      private  val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        private const val BASE_URL = "https://notes-android.herokuapp.com/"
        fun create(): MyApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MyApi::class.java)
        }
    }
}
