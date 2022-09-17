package com.example.onlinenotebookapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlinenotebookapp.databinding.FragmentSignUpBinding
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val api = MyApi.create()
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(api))
        ).get(MainViewModel::class.java)

        binding.toSignInTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_sigInFragment)
        }

        binding.nextButton.setOnClickListener {
            createUserAndSendRequest()
        }
        viewModel.for_createUser.observe(viewLifecycleOwner, Observer {
            val action = SignUpFragmentDirections.actionSignUpFragmentToListFragment(it.id, it.nickName)
            findNavController().navigate(action)
            println("createUser:$it")
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            println(it)
        })

        return binding.root
    }

    private fun createUserAndSendRequest() {
        val nickname = binding.editTextTextPersonName2.text.toString()
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (inputCheck(nickname, email, password)) {
            viewModel.createUser(nickname, email, password)
        }
    }

    private fun inputCheckForSignIn(user_name: String, password: String): Boolean {
        return !(TextUtils.isEmpty(user_name) && TextUtils.isEmpty(password))
    }

    private fun inputCheck(user_name: String, email: String, password: String): Boolean {
        return !(TextUtils.isEmpty(user_name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(
            password
        ))
    }

}