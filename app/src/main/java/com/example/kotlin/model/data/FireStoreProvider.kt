package com.example.kotlin.model.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.model.api.NoteResult
import com.example.kotlin.model.entity.Note
import com.example.kotlin.model.entity.User
import com.example.kotlin.model.errors.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private const val USERS_COLLECTION = "users"
private const val NOTES_COLLECTION = "notes"

class FireStoreProvider(private val firebaseAuth: FirebaseAuth,
                        private val db: FirebaseFirestore) : DataProvider {
    private val currentUser
        get() = firebaseAuth.currentUser

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let {
                User(it.displayName ?: "", it.email ?: "")
            }
        }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().addSnapshotListener { snapshot, throwable ->
                    value = throwable?.let { throw it }
                            ?: snapshot?.let {
                        val notes = it.documents.map { doc -> doc.toObject(Note::class.java) }
                        NoteResult.Success(notes)
                    }
                }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun getNote(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().document(id).get()
                    .addOnSuccessListener { value = NoteResult.Success(it.toObject(Note::class.java)) }
                    .addOnFailureListener { throw it }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun saveNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().document(note.id).set(note)
                    .addOnSuccessListener { value = NoteResult.Success(note) }
                    .addOnFailureListener { throw it }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }

    override fun deleteNote(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                getUserNotesCollection().document(id).delete()
                    .addOnSuccessListener { value = NoteResult.Success(null) }
                    .addOnFailureListener { throw it }
            } catch (e: Throwable) {
                value = NoteResult.Error(e)
            }
        }
}
