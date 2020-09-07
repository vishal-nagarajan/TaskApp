package com.example.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year

@Entity
data class Note(
    val task: String,
    val description:String,
    val client:String,
    val project:String,
    val startDay: Int,
    val startMon: Int,
    val startYear: Int,
    val endDay:Int,
    val endMon: Int,
    val endYear:Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}


