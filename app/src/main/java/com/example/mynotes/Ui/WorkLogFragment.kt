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
    private fun copyFile(DestinationContentUri: Uri, fileToExport: File){
       context?.contentResolver?.openFileDescriptor(DestinationContentUri, "W")
           .use { parcelFileDescriptor ->
           ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor).write(fileToExport.readBytes())
       }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_log, container, false)
    }

//    @RequiresApi(Build.VERSION_CODES.Q)
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
//
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
                        .setCellValue((time.updatedDay).toString() + (time.updatedMon).toString())
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
                val shareuri = Uri.parse(
                    context?.getExternalFilesDir(DIRECTORY_DOWNLOADS)
                        .toString() + "/FolderName/File.xlsx"
                )


            context?.toast(getExternalStorageDirectory().toString())
//            try create file in Media store
//            val values = ContentValues()

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "Test")
//                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
//                values.put(
//                    MediaStore.MediaColumns.RELATIVE_PATH,
//                    "${Environment.DIRECTORY_DOWNLOADS}/TaskApp/"
//                )
////                values.put(MediaSto)
////            }       //folder name
//                context?.contentResolver?.insert(MediaStore.Files.getContentUri("external"), values)
//
//            }
//            context?.toast(MediaStore.Files.getContentUri("external").toString())


//                context?.toast(Environment.getExternalStoragePublicDirectory(
//                    DIRECTORY_DOWNLOADS).toString())

//call
//            val callIntent: Intent = Uri.parse("tel:5551234").let { number ->
//                Intent(Intent.ACTION_DIAL, number)
//            }
//            startActivity(callIntent)
//sharing woked well
//            val shareIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(
//                    Intent.EXTRA_STREAM,
//                shareuri
//                )
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//                type = "application/vnd.ms-excel.xlsx"
//            }
//            startActivity(Intent.createChooser(shareIntent, "share"))
//view through intent
//            val opifle = context?.contentResolver?.openFile(contentUri,"r",null)
//            val viewIntent = Intent().apply {
//                setAction(Intent.ACTION_VIEW)
//                setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//                putExtra(Intent.EXTRA_STREAM,shareuri)
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//////                setData()
////                setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
////                setData(Uri.parse("/storage/emulated/0/Android/data/com.example.mynotes/files/Download/FolderName/File.xlsx"))
////                setDataAndType(shareuri,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
////                setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//            }
//            activity?.startActivity(Intent.createChooser(viewIntent,"app open"))
//openfile
//            val PICK_PDF_FILE = 2
////
//            fun openFile(pickerInitialUri: Uri) {
//    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
//        addCategory(Intent.CATEGORY_OPENABLE)
//        type = "application/vnd.ms-excel.xlsx"
//
//        // Optionally, specify a URI for the file that should appear in the
//        // system file picker when it loads.
//        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
//    }
//
//    startActivityForResult(intent, PICK_PDF_FILE)
//}
//            openFile(contentUri)
//                Uri.parse("content://storage/emulated/0/Android/data/com.example.mynotes/files/Download"))
//            Uri.fromFile(context?.getExternalFilesDir(DIRECTORY_DOCUMENTS))





////worked saving  of a new fle
//            val CREATE_FILE = 1
//                val intent = Intent(ACTION_CREATE_DOCUMENT).apply {
////                    addCategory(Intent.CATEGORY_OPENABLE)
//                    putExtra(EXTRA_STREAM,contentUri)
//                    addFlags(FLAG_GRANT_READ_URI_PERMISSION)
//                    type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//                    putExtra(EXTRA_TITLE, "invoice.xlsx")
//
//                }
//            activity?.startActivity(createChooser(intent,""))
//                startActivityForResult(intent, CREATE_FILE)
//            val CREATE_FILE = 1
//            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//                addCategory(Intent.CATEGORY_OPENABLE)
//                val fileName =  "file.xlsx"
//
//
//                var file = File(fileName)
//
////                // create a new file
////                val isNewFileCreated :Boolean = file.createNewFile()
////
////                if(isNewFileCreated){
////                    context?.toast("$fileName is created successfully.")
////                } else{
////                    context?.toast("$fileName already exists.")
////                }
////
////                // try creating a file that already exists
////                val isFileCreated :Boolean = file.createNewFile()
////
////                if(isFileCreated){
////                    context?.toast("$fileName is created successfully.")
////                } else{
////                    context?.toast("$fileName already exists.")
////                }
//
//                type = "application/xlsx"
//                putExtra(Intent.EXTRA_TITLE, fileName)
////                putExtra(Intent.)
////                    putExtra(Intent.,"vishal")
//            }
//            startActivityForResult(intent, CREATE_FILE)


        }

    }


}