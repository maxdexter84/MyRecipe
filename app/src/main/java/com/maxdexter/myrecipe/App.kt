package com.maxdexter.myrecipe

import android.app.Application
import androidx.room.Room
import com.maxdexter.myrecipe.database.AppDatabase

class App :Application() {
    companion object {
        var db: AppDatabase? = null
        fun getDatabase(): AppDatabase? {
            return db
        }
    }

    override fun onCreate() {
        super.onCreate()
        db= Room.databaseBuilder(applicationContext, AppDatabase::class.java,"DB").build()
    }
}