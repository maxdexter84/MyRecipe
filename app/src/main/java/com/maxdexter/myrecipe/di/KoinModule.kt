package com.maxdexter.myrecipe.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.maxdexter.myrecipe.database.firestore.FireStoreProvider
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.repository.NoteRepository
import org.koin.dsl.module.module
import kotlin.math.sin

val appModule = module{
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { AppDatabase.invoke(get())}
    single { NoteRepository(get(),get()) }

}
