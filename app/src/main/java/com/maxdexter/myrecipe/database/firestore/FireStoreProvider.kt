package com.maxdexter.myrecipe.database.firestore

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.maxdexter.myrecipe.database.NoAuthException
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import java.lang.Exception
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

    @ExperimentalCoroutinesApi
    override suspend fun subscribeToAllNotes(): ReceiveChannel<MutableList<Recipe>> =
        Channel<MutableList<Recipe>>(Channel.CONFLATED).apply {
            var registration: ListenerRegistration? = null
            try {
               registration = getUserNotesCollection().addSnapshotListener { snapshot , e->
                 val list = snapshot?.documents?.map { it.toObject(Recipe::class.java)!! } as MutableList<Recipe>
                   offer(list)
                    if (e != null) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }catch (e: Throwable) {
                Log.e(TAG, e.stackTraceToString())
            }
            invokeOnClose { registration?.remove() }
        }

    override suspend fun saveNote(recipe: Recipe): Recipe =
        suspendCoroutine{continuation ->
            try {
                getUserNotesCollection().document(recipe.uuid)
                    .set(recipe).addOnSuccessListener {
                        Log.d(TAG, "Note $recipe is saved")
                        continuation.resume(recipe)
                    }.addOnFailureListener {
                        Log.d(TAG, "Error saving note $recipe, message: ${it.message}")
                        continuation.resumeWithException(it)
                    }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }

    override suspend fun getNoteById(uuid: String): Recipe =
        suspendCoroutine{continuation ->
            try {
                getUserNotesCollection().document(uuid).get()
                    .addOnSuccessListener {
                        continuation.resume(it.toObject(Recipe::class.java)!!)
                    }.addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
                //Log.e("TAG", e.stackTraceToString())
            }
        }

    override suspend fun getCurrentUser(): User? =
        suspendCoroutine{continuation ->
            continuation.resume(currentUser.let {
                if(it?.displayName == null && it?.email == null) null else it.displayName?.let { it1 ->
                    it.email?.let { it2 ->
                        User(
                            it1, it2
                        )
                    }
                }
            })

        }

    override suspend fun deleteNote(recipe: Recipe): Boolean =
        suspendCoroutine{ continuation ->
      try {
          getUserNotesCollection().document(recipe.uuid).delete()
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