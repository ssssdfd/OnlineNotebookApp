package com.example.onlinenotebookapp.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinenotebookapp.MyApi
import com.example.onlinenotebookapp.databinding.RoomItemBinding
import com.example.onlinenotebookapp.model.room.GetRoomsFromDbItem
import com.example.onlinenotebookapp.model.room.RoomResponse
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory

class RoomAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    var receivedRooms = mutableListOf<GetRoomsFromDbItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class RoomViewHolder(val binding: RoomItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val currentReceivedRooms = receivedRooms[position]
        with(holder.binding) {
            setRoomNameTv.text = currentReceivedRooms.roomName
            setRoomIdTv.text = currentReceivedRooms.id.toString()

        }
       holder.itemView.setOnClickListener {
           onClickListener.clickListener(currentReceivedRooms)
       }
    }

    override fun getItemCount(): Int {
        return receivedRooms.size
    }

    class OnClickListener(val clickListener:(item:GetRoomsFromDbItem)->Unit){
        fun onClick(item: GetRoomsFromDbItem) = clickListener(item)
    }



}