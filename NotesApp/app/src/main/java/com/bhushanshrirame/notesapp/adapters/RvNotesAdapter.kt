package com.bhushanshrirame.notesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bhushanshrirame.notesapp.R
import com.bhushanshrirame.notesapp.databinding.NoteItemLayotBinding
import com.bhushanshrirame.notesapp.fragments.NoteFragmentDirections
import com.bhushanshrirame.notesapp.model.Note
import com.bhushanshrirame.notesapp.utils.hideKeyBoard
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.commonmark.node.SoftLineBreak

class RvNotesAdapter:androidx.recyclerview.widget.ListAdapter<Note,RvNotesAdapter.NotesViewHolder>(DiffUtilCallback()) {
    inner class NotesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val contentBinding = NoteItemLayotBinding.bind(itemView)
        val title: MaterialTextView =contentBinding.noteItemTitle
        val content: TextView = contentBinding.noteContentItem
        val date: MaterialTextView = contentBinding.noteDate
        val parent: MaterialCardView = contentBinding.noteItemLayoutparent
        val markWon = Markwon.builder(itemView.context)
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TaskListPlugin.create(itemView.context))
            .usePlugin(object: AbstractMarkwonPlugin(){
                override fun configureVisitor(builder: MarkwonVisitor.Builder) {
                    super.configureVisitor(builder)
                    builder.on(
                        SoftLineBreak::class.java
                    ){visitor , _ ->visitor.forceNewLine()}
                }
            })
            .build()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item_layot,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        getItem(position).let { note->
            holder.apply {

                parent.transitionName="recylerView${note.id}"
                title.text=note.title
                markWon.setMarkdown(content,note.content)
                date.text=note.date
                parent.setCardBackgroundColor(note.color)

                itemView.setOnClickListener{

                    val action=NoteFragmentDirections.actionNoteFragmentToSaveOrDeleteFragment2()
                        .setNote(note)
                    val extras = FragmentNavigatorExtras(parent to "recylerView_${note.id}")
                    it.hideKeyBoard()
                    Navigation.findNavController(it).navigate(action,extras)

                }
                content.setOnClickListener{
                    val action=NoteFragmentDirections.actionNoteFragmentToSaveOrDeleteFragment2()
                        .setNote(note)
                    val extras = FragmentNavigatorExtras(parent to "recylerView_${note.id}")
                    it.hideKeyBoard()
                    Navigation.findNavController(it).navigate(action,extras)

                }
            }
        }
    }
}