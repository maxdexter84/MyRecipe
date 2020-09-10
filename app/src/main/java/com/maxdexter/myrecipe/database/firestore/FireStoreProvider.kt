package com.maxdexter.myrecipe.database.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import com.maxdexter.myrecipe.model.Note
import com.maxdexter.myrecipe.model.NoteResult
import java.lang.Exception

private const val NOTES_COLLECTION = "notes"
class FireStoreProvider : RemoteDataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)


    override fun subscribeToAllNotes(): LiveData<MutableList<Note>> {
        val result = MutableLiveData<MutableList<Note>>()

        notesReference.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (e != null) {
                    Log.e("FIRESTORE","$e")
                } else if (snapshot != null) {
                    val notes = mutableListOf<Note>()

                    for (doc: QueryDocumentSnapshot in snapshot) {
                        notes.add(doc.toObject(Note::class.java))
                    }
                    result.value = notes
                }
            }
        })

        return result
    }

    override fun getNoteById(uuid: String): LiveData<Note> {
        val result = MutableLiveData<Note>()
        notesReference.document(uuid)
            .get().addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot>{
                override fun onSuccess(snapshot: DocumentSnapshot?) {
                    result.value = snapshot?.toObject(Note::class.java)
                }

            }).addOnFailureListener(object : OnFailureListener{
                override fun onFailure(p0: Exception) {
                    Log.d(TAG, "Error saving note $uuid, message: ${p0.message}")
                }
            })
        return result
    }

    override fun saveNote(note: Note): LiveData<Note> {
        val result = MutableLiveData<Note>()
        notesReference.document(note.uuid)
            .set(note).addOnSuccessListener(object : OnSuccessListener<Void>{
                override fun onSuccess(p0: Void?) {
                    Log.d(TAG, "Note $note is saved")
                    result.value = note
                }

            }).addOnFailureListener(object : OnFailureListener{
                override fun onFailure(p0: Exception) {
                    Log.d(TAG, "Error saving note $note, message: ${p0.message}")
                }
            })
        return result
    }
}