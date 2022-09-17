package com.example.onlinenotebookapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onlinenotebookapp.databinding.FragmentEditNoteBinding
import com.example.onlinenotebookapp.databinding.NoteItemBinding
import com.example.onlinenotebookapp.repository.MainRepository
import com.example.onlinenotebookapp.viewModel.MainViewModel
import com.example.onlinenotebookapp.viewModel.MyViewModelFactory
import kotlinx.coroutines.delay

class EditNoteFragment : Fragment(),MenuProvider {
    private lateinit var binding: FragmentEditNoteBinding
    private val api = MyApi.create()
    private lateinit var viewModel : MainViewModel
    private val args by navArgs<EditNoteFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container,false)

        if (args.noteContent!=""){
            binding.saveImageButton.visibility = View.GONE
            binding.updateImageView.visibility = View.VISIBLE
        }

        binding.noteEd.setText(args.noteContent)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(api))).get(MainViewModel::class.java)

        binding.saveImageButton.setOnClickListener {
            makeNote()
        }

        binding.updateImageView.setOnClickListener {
            updateNote()
        }

        Toast.makeText(requireContext(), "id:${args.noteId}", Toast.LENGTH_SHORT).show()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun makeNote(){
        val content = binding.noteEd.text.toString()
        if (inputCheck(content)){
            viewModel.createNote(content,args.roomId,args.userId)
            Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNote(){
        val content = binding.noteEd.text.toString()
        val noteid = args.noteId
        if (inputCheck(content)){
            viewModel.updateNote(noteid, content)
            Toast.makeText(requireContext(), "Note updated", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(note:String): Boolean{
        return !(TextUtils.isEmpty(note))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteNote_item->{
                Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                viewModel.deleteNote(args.noteId)
                true
            }else->false
        }
    }
}
