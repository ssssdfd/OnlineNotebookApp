package com.example.onlinenotebookapp

import android.os.Binder
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinenotebookapp.databinding.FragmentListBinding
import com.example.onlinenotebookapp.databinding.FragmentRoomBinding
import com.example.onlinenotebookapp.model.notes.GetNotesFromRoomItem
import com.example.onlinenotebookapp.recyclerView.NoteAdapter
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory
import java.lang.IllegalArgumentException

class RoomFragment : Fragment(),MenuProvider {
    private val args by navArgs<RoomFragmentArgs>()
    private lateinit var binding:FragmentRoomBinding
    private lateinit var viewModel: MainViewModel
    private val api = MyApi.create()
    private var receivedNotes = mutableListOf<GetNotesFromRoomItem>()
    private  lateinit var noteAdapter: NoteAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this,MyViewModelFactory(MainRepository(api))).get(MainViewModel::class.java)

        binding.apply {
            roomNameTv.text = args.roomName
            roomIdTv.text = args.roomId.toString()
            userNameTv.text = args.userName
        }

        binding.addNoteBtn.setOnClickListener {
            addNote()
        }

        noteAdapter = NoteAdapter(NoteAdapter.OnClickListener{
            Toast.makeText(requireContext(), "Note:${it.id}", Toast.LENGTH_SHORT).show()
            val note_id = it.id
            val action = RoomFragmentDirections.actionRoomFragmentToEditNoteFragment(it.content,args.roomId,args.userId,note_id)
            findNavController().navigate(action)
        })

        binding.apply {
            noteRcView.adapter = noteAdapter
            noteRcView.layoutManager =LinearLayoutManager(requireContext())
        }

        getNote()
        viewModel.for_getNotesFromRoom.observe(viewLifecycleOwner){
            receivedNotes = it as MutableList<GetNotesFromRoomItem>
            noteAdapter.receivedNotes =receivedNotes
        }

        binding.getUsersImageButton.setOnClickListener {
            val action = RoomFragmentDirections.actionRoomFragmentToAddUserFragment(args.roomId)
            findNavController().navigate(action)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }




    private fun addNote(){
        val action = RoomFragmentDirections.actionRoomFragmentToEditNoteFragment("",args.roomId, args.userId,0)
       findNavController().navigate(action)
    }

    private fun  getNote(){
        viewModel.getNotesFromRoom(args.roomId)
    }

    private fun inputCheck(note:String): Boolean{
        return !(TextUtils.isEmpty(note))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.room_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteRoom_item ->{
                Toast.makeText(requireContext(), "Click delete room", Toast.LENGTH_SHORT).show()
                viewModel.deleteRoom(args.roomId, args.userId)
                true}
            R.id.exitRoom_item-> {
                Toast.makeText(requireContext(), "You left the room", Toast.LENGTH_SHORT).show()
                viewModel.exitFromRoom(args.roomId, args.userId)
                true
            }
            else -> false
        }
    }

}

