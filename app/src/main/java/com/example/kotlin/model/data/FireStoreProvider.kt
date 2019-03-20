package com.example.kotlin.model.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note
import com.google.firebase.firestore.FirebaseFirestore

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : DataProvider {
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun subscribeToNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.addSnapshotListener { snapshot, throwable ->
                value = throwable?.let {
                    NoteResult.Error(it)
                } ?: snapshot?.let {
                    val notes = it.documents.map { doc -> doc.toObject(Note::class.java) }
                    NoteResult.Success(notes)
                }
            }
        }

    override fun getNote(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.document(id).get()
                .addOnSuccessListener { value = NoteResult.Success(it.toObject(Note::class.java)) }
                .addOnFailureListener { value = NoteResult.Error(it) }
        }

    override fun saveNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesReference.document(note.id).set(note)
                .addOnSuccessListener {
                    value = NoteResult.Success(note)
                }
                .addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }
}