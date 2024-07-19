package com.application.milsat.android.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FormDao {
    @Query("SELECT * FROM form")
    fun getAll(): List<Form>

    @Insert
    fun insertAll(vararg form: Form)

    @Delete
    fun delete(form: Form)
}