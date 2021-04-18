package com.example.mynotes.Ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context.DOWNLOAD_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.*
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

import androidx.core.content.FileProvider.getUriForFile
import androidx.navigation.ActivityNavigator
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.db.TaskWithTimelog
import kotlinx.android.synthetic.main.dialog_file_name.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_work_log.*
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@Suppress("DEPRECATION")
class WorkLogFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_log, container, false)
    }

    @SuppressLint("SdCardPath")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lateinit var notedata:List<Note>
        lateinit var timelogData:List<TaskWithTimelog>
        launch {
            context?.let {
                notedata=NoteDatabase(it).getNoteDao().getAllNotes()
                timelogData=NoteDatabase(it).getNoteDao().getTaskWithTimeLog()
            }
        }

        button_generat_work_log.setOnClickListener { it ->
            //getting permission to write and read data from device storage
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }

            val workbook: Workbook = XSSFWorkbook()
            var sheet: Sheet = workbook.createSheet("log")
            var row = sheet.createRow(0)
            sheet.createRow(0).createCell(0).setCellValue("Task")
            sheet.createRow(1).createCell(0).setCellValue("project")
            sheet.createRow(2).createCell(0).setCellValue("client")
            sheet.createRow(3).createCell(0).setCellValue("description")
            sheet.createRow(4).createCell(0).setCellValue("start date")
            sheet.createRow(5).createCell(0).setCellValue("end date")
            sheet.createRow(6).createCell(0).setCellValue("place")
            sheet.createRow(7).createCell(0).setCellValue("people")
            sheet.createRow(8).createCell(0).setCellValue("nature of work")
            sheet.createRow(9).createCell(0).setCellValue("Status")
            sheet.createRow(10).createCell(0).setCellValue("from time")

            var i=1
            var j=1
            for(note in notedata){
                i=0
                sheet.getRow(i++).createCell(j).setCellValue(note.task)
                sheet.getRow(i++).createCell(j).setCellValue(note.project)
                sheet.getRow(i++).createCell(j).setCellValue(note.client)
                sheet.getRow(i++).createCell(j).setCellValue(note.description)
                sheet.getRow(i++).createCell(j).setCellValue(note.startDay.toString() + "/" + note.startMon.toString() + "/" + note.startYear.toString())
                sheet.getRow(i++).createCell(j).setCellValue("${note.endDay} / ${note.endMon}/${note.endYear}")
                sheet.getRow(i++).createCell(j).setCellValue(note.place)
                sheet.getRow(i++).createCell(j).setCellValue(note.people)
                sheet.getRow(i++).createCell(j).setCellValue(note.natureOfWork)
                sheet.getRow(i++).createCell(j).setCellValue(note.Status)
                sheet.getRow(i).createCell(j).setCellValue("${note.fromtimeHour} : ${note.fromtimeMin}")
                i=0
                j++
            }

            //time logs in seperate sheet of the same workbook using on to many
            sheet = workbook.createSheet("timelog")
            var ntasks = 0
            while (ntasks<timelogData.size*5) {
                sheet.createRow(ntasks+0).createCell(0).setCellValue("Task")
                sheet.createRow(ntasks+1).createCell(0).setCellValue("Date")
                sheet.createRow(ntasks+2).createCell(0).setCellValue("From time")
                sheet.createRow(ntasks+3).createCell(0).setCellValue("To time")
                ntasks+=5
            }
            i=0
            j=1
            for(taskandtime in timelogData){
                j=1
                sheet.getRow(i).createCell(j).setCellValue(taskandtime.task.task)
                for(time in taskandtime.timelogs) {
                    sheet.getRow(i + 1).createCell(j)
                        .setCellValue((time.updatedDay).toString() +"/"+ (time.updatedMon).toString() +"/"+ (time.updatedYear).toString())
                    sheet.getRow(i + 2).createCell(j)
                        .setCellValue((time.fromtimeHour).toString()+":" + (time.fromtimeMin).toString())
                    sheet.getRow(i + 3).createCell(j)
                        .setCellValue((time.totimeHour).toString() +":"+ (time.totimeMin).toString())
                    j++
                }
                i+=5
            }

            var fileName:String = filename.text.toString()
            if (fileName.isEmpty()){
                filename.error = "Document title required"
                filename.requestFocus()
                return@setOnClickListener
            }else{
                fileName = fileName+".xlsx"
            }



                val extStorageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

//            getExternalStorageDirectory()
//                    Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
//                    context?.getExternalFilesDir(DIRECTORY_DOWNLOADS)
//                "/data/data/com.example.mynotes"
//                File("/storage/emulated/0")
//            "/data/data/com.example.mynotes"
//                "/sdcard/Alarms"
//            context?.getExternalFilesDir(null)?.absoluteFile
//            "/data/data/com.example.mynotes/FolderName/File2.xlsx"

                val folder = File(extStorageDirectory, "Work Logs")
                folder.mkdir()
                val file = File(folder, fileName)
                file.createNewFile()
                try {
                    file.createNewFile()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

                try {
                    val fileOut = FileOutputStream(file)
                    workbook.write(fileOut)
                    fileOut.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            context?.toast("Your file is saved in"+folder.absolutePath+fileName)
        }

    }


}