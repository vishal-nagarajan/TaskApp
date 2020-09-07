package com.example.mynotes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes() : List<Note>

//    @Query("Select startDay from note Order by startDay asc")
//    suspend fun getAlldates():List<Int>

//    @Query("SELECT task FROM note")
//    suspend fun getALlTask() : List<Note>

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)


    @Update
    suspend fun updateNote(note: Note)
}