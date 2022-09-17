package com.example.onlinenotebookapp.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinenotebookapp.databinding.UserItemBinding
import com.example.onlinenotebookapp.model.getAllUsers.GetAllUsersResponseItem

class UserAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var receivedUsers = emptyList<GetAllUsersResponseItem>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

    class UserViewHolder(val binding:UserItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
      val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
      val currentUser = receivedUsers[position]
        with(holder.binding){
            setUserNameTv.text = currentUser.nickName
            setUserIdTv.text = currentUser.id.toString()
        }

        holder.itemView.setOnClickListener {
            onClickListener.clickListener(currentUser)
        }
    }

    override fun getItemCount(): Int {
        return receivedUsers.size
    }

    class OnClickListener(val clickListener: (item:GetAllUsersResponseItem)->Unit){
        fun onClick(item:GetAllUsersResponseItem) = clickListener(item)
    }
}