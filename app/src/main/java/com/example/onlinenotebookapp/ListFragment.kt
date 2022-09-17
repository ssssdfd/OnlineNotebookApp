package com.example.onlinenotebookapp

import android.content.ClipData
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinenotebookapp.databinding.FragmentListBinding
import com.example.onlinenotebookapp.model.room.GetRoomsFromDbItem
import com.example.onlinenotebookapp.model.room.RoomResponse
import com.example.onlinenotebookapp.recyclerView.RoomAdapter
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory


class ListFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation
    private lateinit var binding: FragmentListBinding
    private lateinit var roomAdapter :RoomAdapter
    var receivedRooms = mutableListOf<GetRoomsFromDbItem>()
    private val api = MyApi.create()
    private val args: ListFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_in)
        outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_out)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(api))).get(MainViewModel::class.java)

        receivedRooms()

        binding.setUserIdTextView.text = args.userId.toString()
        binding.setUserNameTextView.text = args.userName

        binding.addRoomBtn.setOnClickListener {
           binding.apply {
                roomCard.startAnimation(inAnimation)
                roomCard.visibility = View.VISIBLE
            }
        }


        binding.closeRoomCreateBtn.setOnClickListener {
            binding.apply {
                roomCard.startAnimation(outAnimation)
                roomCard.visibility = View.GONE
            }
        }

        binding.createRoomBtn.setOnClickListener {
            createRoom()
        }

        roomAdapter = RoomAdapter(RoomAdapter.OnClickListener{
            val action = ListFragmentDirections.actionListFragmentToRoomFragment(it.roomName, it.id, args.userName, args.userId)
        findNavController().navigate(action)})


        binding.apply {
            rcViewRooms.adapter = roomAdapter
            rcViewRooms.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.for_getRooms.observe(viewLifecycleOwner){
            receivedRooms = it as MutableList<GetRoomsFromDbItem>
            roomAdapter.receivedRooms = receivedRooms
        }

        viewModel.for_createRoom.observe(viewLifecycleOwner){
            receivedRooms()
        }

        return binding.root
    }

    private fun createRoom() {
        val email = binding.emailRoom.text.toString()
        val room_name = binding.roomName.text.toString()
        if (inputCheck(email, room_name)) {
                viewModel.createRoom(email,room_name)
        }
    }

    private fun receivedRooms() {
        viewModel.getRooms(args.userId)
    }

    private fun inputCheck(email: String, room_name: String): Boolean {
        return !(TextUtils.isEmpty(email) && TextUtils.isEmpty(room_name))
    }



}