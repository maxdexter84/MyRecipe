package com.maxdexter.myrecipe.database.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.maxdexter.myrecipe.database.NoAuthException
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult
import com.maxdexter.myrecipe.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "notes"
private const val USERS_COLLECTION = "users"
class FireStoreProvider(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : RemoteDataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val currentUser
        get() = auth.currentUser



    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun subscribeToAllNotes(): LiveData<MutableList<Note>> =
        MutableLiveData<MutableList<Note>>().apply {
            try {
                getUserNotesCollection().addSnapshotListener { snapshot , e->
                    value = snapshot?.documents?.map { it.toObject(Note::class.java)!! } as MutableList<Note>?
                    if (e != null) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }catch (e: Throwable) {
                Log.e(TAG, e.stackTraceToString())
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
                Log.e("TAG", e.stackTraceToString())
            }
        }

    override suspend fun getNoteById(uuid: String): Note =
        suspendCoroutine{continuation ->
            try {
                getUserNotesCollection().document(uuid).get()
                    .addOnSuccessListener {
                        continuation.resume(it.toObject(Note::class.java)!!)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
                //Log.e("TAG", e.stackTraceToString())
            }
        }

    override suspend fun getCurrentUser(): User =
        suspendCoroutine{continuation ->
            continuation.resume(currentUser.let { User(it?.displayName ?: "",
                it?.email ?: "") })
        }

    override suspend fun deleteNote(note: Note): Boolean =
        suspendCoroutine{ continuation ->
      try {
          getUserNotesCollection().document(note.uuid).delete()
              .addOnSuccessListener {
                  continuation.resume(true)
                  Log.d(TAG, "DocumentSnapshot successfully deleted!")
              }.addOnFailureListener {
                  continuation.resumeWithException(it)
              }
      }catch (e: Exception){
          continuation.resumeWithException(e)
      }

    }


}