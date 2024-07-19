package com.application.milsat.android.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Form::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun formDao(): FormDao
}