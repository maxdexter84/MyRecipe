package com.maxdexter.myrecipe.di

import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.repository.NoteRepository
import org.koin.dsl.module.module

val appModule = module{
    single { AppDatabase.invoke(get())}
    single { NoteRepository(get()) }

}
