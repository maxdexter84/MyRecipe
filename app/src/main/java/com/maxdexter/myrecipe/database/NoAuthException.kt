package com.maxdexter.myrecipe.database

import android.util.Log

class NoAuthException: Throwable() {
    init {
        Log.e("TAG", "вы не авторизованны")
    }
}