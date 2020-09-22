package com.example.mynotes.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.util.*

@Entity
data class Note(
    val task: String,
    val description:String,
    val client:String,
    val project:String,
    val day:Int,
    val mon:Int,
    val year:Int,
    val startDay: Int,
    val startMon: Int,
    val startYear: Int,
    val endDay:Int,
    val endMon: Int,
    val endYear:Int,
    val Status:Boolean,
    val hours:Int,
    val fromtimeHour: Int,
    val fromtimeMin: Int,
    val totimeHour:Int,
    val totimeMin: Int,
    val place:String,
    val people:String,
    val natureOfWork:String
    ):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}
@Entity
data class client(
    val client: String
){
    @PrimaryKey(autoGenerate = true)
    var clienrId:Int=0

}
@Entity
data class project(
    val project: String
){
    @PrimaryKey(autoGenerate = true)
    var projectId:Int=0

}
@Entity
data class NOW(
    val nowChip:String="a"
){
    @PrimaryKey(autoGenerate = true)
    var nowId:Int=0

}

@Entity
data class Timelog(
    val updatedDay: Int,
    val updatedMon:Int,
    val updatedYear:Int,
    val fromtimeHour: Int,
    val fromtimeMin: Int,
    val totimeHour:Int,
    val totimeMin: Int,
    val taskid:Int
)
{
    @PrimaryKey(autoGenerate = true)
    var timelogId:Int=0

}

data class TaskWithTimelog(
    @Embedded val task: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskid"
    )
    val timelogs: List<Timelog>
)
//data class Notewithclient(
//    @Embedded val note:Note,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "clientId"
//)val clients : List<client>
//)