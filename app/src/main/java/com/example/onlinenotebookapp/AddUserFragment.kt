package com.example.onlinenotebookapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinenotebookapp.databinding.FragmentAddUserBinding
import com.example.onlinenotebookapp.recyclerView.UserAdapter
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory

class AddUserFragment : Fragment() {
    private lateinit var binding:FragmentAddUserBinding
    private lateinit var viewModel:MainViewModel
    private lateinit var userAdapter: UserAdapter
    private val api = MyApi.create()
    private val args by navArgs<AddUserFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddUserBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(api))).get(MainViewModel::class.java)

        getUsers()

        userAdapter = UserAdapter(UserAdapter.OnClickListener{
            viewModel.enterRoom(args.roomId, it.id)
            Toast.makeText(requireContext(), "User ${it.id} Room:${args.roomId}", Toast.LENGTH_SHORT).show()
        })

        viewModel.for_getAllUsers.observe(viewLifecycleOwner){
            userAdapter.receivedUsers =it
        }

        binding.apply {
            userRcView.adapter = userAdapter
            userRcView.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    private fun getUsers(){
        viewModel.getAllUsers()
    }


}