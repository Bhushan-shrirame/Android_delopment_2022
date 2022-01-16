package com.bhushanshrirame.notesapp.repository

import com.bhushanshrirame.notesapp.db.NoteDatabase
import com.bhushanshrirame.notesapp.model.Note

class NoteRepository (private val db: NoteDatabase){

    fun getNote() = db.getNoteDao().getAllNotes();

    fun searchNote(query: String) = db.getNoteDao().seachNote(query)

    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)


}