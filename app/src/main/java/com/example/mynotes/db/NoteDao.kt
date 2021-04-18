package com.example.mynotes.db

import androidx.room.*
import com.example.mynotes.Ui.updateStatus
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

@Dao
interface NoteDao {


    @Insert
    suspend fun addNote(note: Note)

    @Insert
    suspend fun addClient(client: client)

    @Insert
    suspend fun addNOW(now: NOW)

    @Insert
    suspend fun addProject(project: project)

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)

    @Insert
    suspend fun addTimeLog(timelog: Timelog)

    @Update
    suspend fun updateNote(note: Note)

    @Transaction
    @Query("SELECT * FROM Note ")
    suspend fun getTaskWithTimeLog():List<TaskWithTimelog>

    @Query("SELECT taskid FROM Timelog WHERE (((updatedDay>:fromDay AND  updatedMon=:fromMonth AND updatedYear=:fromYear) OR(updatedMon>:fromMonth AND updatedYear=:fromMonth) OR (updatedYear>:fromYear))AND((updatedDay<:toDay AND updatedMon=:toMonth AND updatedYear=:toYear) OR (updatedMon<:toMonth AND updatedYear=:toYear)OR (updatedYear<:toYear)))")
    suspend fun getDateLog(fromDay:Int,fromMonth:Int,fromYear:Int,toDay:Int,toMonth:Int,toYear:Int):List<Int>

    @Query("SELECT nowChip from NOW")
    suspend fun getNOW():List<String>

    //refactoring needed
    @Query("UPDATE note SET startDay=:day, startMon=:mon,startYear=:year WHERE id =:did")
    suspend fun updateMDate(did:Int,day:Int,mon:Int,year:Int)

    @Query("UPDATE note SET day=:Day, mon=:Mon, year=:Year")
    suspend fun updateCurrDate(Day:Int,Mon:Int,Year:Int)

    @Query("SELECT client FROM client")
    suspend fun getAllClients():List<String>

    @Query("SELECT project FROM project")
    suspend fun getAllProjects():List<String>


    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes() : List<Note>

    @Query("SELECT * FROM note WHERE ((day>=startDay AND mon>=startMon AND year=startYear) OR (mon>startMon AND year=startYear) OR (year>startYear)) AND Status=0 ORDER BY id DESC")
    suspend fun getTodayNotes() : List<Note>

    @Query("SELECT Distinct client FROM note ")
    suspend fun getClient():List<String>

    @Query("SELECT Distinct project FROM note ")
    suspend fun getProject():List<String>

    @Query("UPDATE note SET Status=:status WHERE id=:sid")
    suspend fun updateStatus(sid:Int,status: Boolean)

    @Query("UPDATE note SET hours =:Hours, place =:Place,people=:People, fromtimeHour=:fhour,fromtimeMin=:fmin,totimeHour =:thour,totimeMin=:tmin ,natureOfWork=:NOF WHERE id=:sid")
    suspend fun updateHours(sid:Int,Hours:Int,Place:String,People:String,fhour:Int,fmin:Int,thour:Int,tmin:Int,NOF:String)

    @Query("SELECT * FROM note WHERE Status=0")
    suspend fun getProgressTasks():List<Note>

    @Query("SELECT * FROM note WHERE Status=1")
    suspend fun getCompletedTasks():List<Note>

    @Query("SELECT * FROM note WHERE Status=0 AND ((day>endDay AND mon=endMon AND year=endYear) OR (mon>endMon AND year=endYear) OR(year>endYear) )")
    suspend fun getOverDueTasks():List<Note>

    @Query("SELECT  * FROM note Where client=:Client")
    suspend fun getClientLog(Client:String):List<Note>

    @Query("SELECT  * FROM note Where project=:Project")
    suspend fun getProjectLog(Project:String):List<Note>

    @Query("SELECT * FROM note WHERE Status=0 AND ((day<=endDay AND mon=endMon AND year=endYear) OR (mon<endMon AND year=endYear) OR(year<endYear) )")
    suspend fun getYetToDueTasks():List<Note>
}