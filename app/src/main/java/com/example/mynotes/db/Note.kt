package com.example.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    val task: String,
    val description:String,
    val client:String,
    val project:String,
    val startDate:String,
    val endDate:String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}


