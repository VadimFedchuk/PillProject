package com.vadimfedchuk.pillstest.ui.main.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills

@Dao
interface PillsDao {

    @get:Query("SELECT * FROM pills")
    val allPills: List<Pills>

    @get:Query("SELECT * FROM pills LIMIT 1")
    val lastPills: Pills? // для проверки таблицы на пустоту

    @Insert
    fun setPills(list: List<Pills>)

    @Query("SELECT * FROM pills WHERE id = :id")
    fun getPill(id: Int): Pills

    @Query("DELETE FROM pills")
    fun deleteAllPills()

}
