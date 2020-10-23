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
private const val RECIPES_COLLECTION = "recipes"
class FireStoreProvider(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : RemoteDataProvider {
    var lastVisible: DocumentSnapshot? = null
    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val currentUser
        get() = auth.currentUser



    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()


    private fun getRecipesCollection() = db.collection(RECIPES_COLLECTION)


//    @ExperimentalCoroutinesApi
//    override suspend fun subscribeToAllNotes(): ReceiveChannel<MutableList<Recipe>> =
//        Channel<MutableList<Recipe>>(Channel.CONFLATED).apply {
//            var registration: ListenerRegistration? = null
//            try {
//               registration = getUserNotesCollection().addSnapshotListener { snapshot , e->
//                 val list = snapshot?.documents?.map { it.toObject(Recipe::class.java)!! } as MutableList<Recipe>
//                   offer(list)
//                    if (e != null) {
//                        Log.e(TAG, e.message.toString())
//                    }
//                }
//            }catch (e: Throwable) {
//                Log.e(TAG, e.stackTraceToString())
//            }
//            invokeOnClose { registration?.remove() }
//        }
    @Suppress("UNCHECKED_CAST")
    @ExperimentalCoroutinesApi
    override suspend fun subscribeToAllNotes(): ReceiveChannel<List<Recipe>> =
        Channel<List<Recipe>>(Channel.CONFLATED).apply {

            try {
                if (lastVisible == null) {
                    getRecipesCollection().limit(6).addSnapshotListener { snapshot , e->
                        if (snapshot != null){
                            val list = snapshot.documents.map { it.toObject(Recipe::class.java) }
                            offer(list as List<Recipe>)
                            lastVisible = snapshot.documents.last()
                            Log.i("SNAPSHOT", "$lastVisible")
                        }

                        if (e != null) {
                            Log.e(TAG, e.message.toString())
                        }
                    }

                }else {
                    getRecipesCollection().limit(6).startAfter(lastVisible).addSnapshotListener { snapshot , e->
                        val list = snapshot?.documents?.map { it.toObject(Recipe::class.java)}
                        offer(list as List<Recipe>)
                        lastVisible = snapshot.documents[3]
                        Log.i("SNAPSHOT", "Else $lastVisible")
                        if (e != null) {
                            Log.e(TAG, e.message.toString())
                        }
                    }
                }
            }catch (e: Throwable) {
                Log.e(TAG, e.stackTraceToString())
            }
        }

//    override suspend fun saveNote(recipe: Recipe): Recipe =
//        suspendCoroutine{continuation ->
//            try {
//                getUserNotesCollection().document(recipe.uuid)
//                    .set(recipe).addOnSuccessListener {
//                        Log.d(TAG, "Note $recipe is saved")
//                        continuation.resume(recipe)
//                    }.addOnFailureListener {
//                        Log.d(TAG, "Error saving note $recipe, message: ${it.message}")
//                        continuation.resumeWithException(it)
//                    }
//            } catch (e: Throwable) {
//                continuation.resumeWithException(e)
//            }
//        }

    override suspend fun saveNote(recipe: Recipe): Recipe =
        suspendCoroutine { continuation ->
            try {
                getRecipesCollection().document(recipe.recipe).set(recipe)
                    .addOnSuccessListener {
                        Log.i("TAG","${recipe.recipe}  Success add")
                        continuation.resume(recipe)

                    }.addOnFailureListener {
                        Log.d(TAG, "Error saving note $recipe, message: ${it.message}")
                       continuation.resumeWithException(it)
                    }
            }catch (e: Exception) {
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