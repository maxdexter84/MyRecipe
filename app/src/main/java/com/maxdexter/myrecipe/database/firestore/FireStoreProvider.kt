package com.maxdexter.myrecipe.database.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult
import com.maxdexter.myrecipe.model.User
import java.lang.Exception

private const val NOTES_COLLECTION = "notes"
private const val USERS_COLLECTION = "users"
class FireStoreProvider : RemoteDataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser


    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToAllNotes(): LiveData<MutableList<Note>> =
        MutableLiveData<MutableList<Note>>().apply {
            try {
                getUserNotesCollection().addSnapshotListener { snapshot , e->
                    value = snapshot?.documents?.map { it.toObject(Note::class.java)!! } as MutableList<Note>?
                }
            }catch (e: Throwable) {

            }
        }

    override fun saveNote(note: Note): LiveData<Note> =
        MutableLiveData<Note>().apply {
            try {
                getUserNotesCollection().document(note.uuid)
                    .set(note).addOnSuccessListener {
                        Log.d(TAG, "Note $note is saved")
                        value = note
                    }.addOnFailureListener {
                        Log.d(TAG, "Error saving note $note, message: ${it.message}")
                        throw it
                    }
            } catch (e: Throwable) {

            }
        }

    override fun getNoteById(uuid: String): LiveData<Note> =
        MutableLiveData<Note>().apply {
            try {

                getUserNotesCollection().document(uuid).get()
                    .addOnSuccessListener {
                        value = it.toObject(Note::class.java)
                    }.addOnFailureListener {
                        throw it
                    }
            } catch (e: Throwable) {

            }
        }

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let { User(it.displayName ?: "",
                it.email ?: "") }
        }
}