package com.example.onlinenotebookapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlinenotebookapp.databinding.FragmentSignInBinding
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val api = MyApi.create()
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(api)))[MainViewModel::class.java]

        binding.nextButton.setOnClickListener {
            signIn()
        }

        binding.deleteUser.setOnClickListener {
            deleteUser()
            binding.editTextTextNickname.setText("")
            binding.editTextTextPassword.setText("")
        }

        viewModel.for_getUser.observe(viewLifecycleOwner) {
            val action =
                SignInFragmentDirections.actionSigInFragmentToListFragment(it.id, it.nickName)
            findNavController().navigate(action)
            println("Get user:${it.nickName}")
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            println(it)
        }

        return binding.root
    }

    private fun signIn() {
        val user_name = binding.editTextTextNickname.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (inputCheck(user_name, password)) {
            viewModel.getUser(user_name, password)
        }
    }

    private  fun deleteUser() {
        val user_name = binding.editTextTextNickname.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (inputCheck(user_name, password)) {
            viewModel.deleteUser(user_name,password)
        }
    }


    private fun inputCheck(user_name: String, password: String): Boolean {
        return !(TextUtils.isEmpty(user_name) && TextUtils.isEmpty(password))
    }

}