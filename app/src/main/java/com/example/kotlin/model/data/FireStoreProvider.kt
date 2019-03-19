package com.example.kotlin.model.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import timber.log.Timber

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : DataProvider {
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun subscribeToNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener { snapshot, throwable ->
            if (throwable != null) {
                result.value = NoteResult.Error(throwable)
            } else if (snapshot != null) {
                val notes = mutableListOf<Note>()

                for (doc: QueryDocumentSnapshot in snapshot) {
                    notes.add(doc.toObject(Note::class.java))
                }

                result.value = NoteResult.Success(notes)
            }
        }

        return result
    }

    override fun getNote(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(id)
            .get()
            .addOnSuccessListener { result.value = NoteResult.Success(it.toObject(Note::class.java)) }
            .addOnFailureListener { result.value = NoteResult.Error(it) }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(note.id)
            .set(note)
            .addOnSuccessListener {
                result.value = NoteResult.Success(note)
            }
            .addOnFailureListener {
                result.value = NoteResult.Error(it)
            }

        return result
    }
}