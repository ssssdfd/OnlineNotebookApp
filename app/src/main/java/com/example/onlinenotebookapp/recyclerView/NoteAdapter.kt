package com.example.onlinenotebookapp.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinenotebookapp.databinding.NoteItemBinding
import com.example.onlinenotebookapp.model.notes.GetNotesFromRoomItem
import com.example.onlinenotebookapp.model.room.GetRoomsFromDbItem

class NoteAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var receivedNotes = emptyList<GetNotesFromRoomItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class NoteViewHolder( val binding: NoteItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
     val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val currentNote = receivedNotes[position]
        with(holder.binding){
            setContetTv.text = currentNote.content
            setIdTextView.text = currentNote.id.toString()
        }

        holder.itemView.setOnClickListener {
            onClickListener.clickListener(currentNote)
        }
    }

    override fun getItemCount(): Int {
        return receivedNotes.size
    }

    class OnClickListener(val clickListener:(item:GetNotesFromRoomItem)->Unit){
        fun onClick(item: GetNotesFromRoomItem) = clickListener(item)
    }
}