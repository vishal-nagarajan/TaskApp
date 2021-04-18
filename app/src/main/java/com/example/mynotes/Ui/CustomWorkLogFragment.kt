package com.example.mynotes.Ui

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_custom_work_log.*
import kotlinx.android.synthetic.main.fragment_work_log.*
import kotlinx.coroutines.*
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CustomWorkLogFragment : BaseFragment() {
    private var note: Note? = null

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
        return inflater.inflate(R.layout.fragment_custom_work_log, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        launch {
            context?.let {
                val array = NoteDatabase(it).getNoteDao().getAllClients()
                val spinner = spinnerclient_CW
                var clientset: String?
                val adapter = context?.let {
                    ArrayAdapter<String>(
                        it,
                        android.R.layout.simple_dropdown_item_1line,
                        array
                    )
                }
                val array2 = NoteDatabase(it).getNoteDao().getAllProjects()
                val spinner2 = spinnerproject_CW
                var projectset: String?
                val adapter2 = context?.let {
                    ArrayAdapter<String>(
                        it,
                        android.R.layout.simple_dropdown_item_1line,
                        array2
                    )
                }
                spinner.adapter = adapter
                spinner2.adapter = adapter2
                arguments.let {
                    clientset = note?.client
                    val index = adapter?.getPosition(clientset)
                    spinner.setSelection(index!!.toInt())
                    projectset = note?.project
                    val index2 = adapter?.getPosition(projectset)
                    spinner2.setSelection(index2!!.toInt())
                }

            }
        }
        lateinit var clientdata:List<Note>
        lateinit var Client:String
        lateinit var projectdata:List<Note>
        lateinit var Project:String

        fun customGenerator(clientd:List<Note>,FolderN:String,FileN:String){
            val workbook: Workbook = XSSFWorkbook()
            val sheet: Sheet = workbook.createSheet("log")
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
                var i=1
                var j=1

                for(note in clientd){
                    i=0
                    sheet.getRow(i++).createCell(j).setCellValue(note.task)
                    sheet.getRow(i++).createCell(j).setCellValue(note.project)
                    sheet.getRow(i++).createCell(j).setCellValue(note.client)
                    sheet.getRow(i++).createCell(j).setCellValue(note.description)
                    sheet.getRow(i++).createCell(j).setCellValue(note.startDay.toString() + "/" + note.startMon.toString() + "/" + note.startYear.toString())
                    sheet.getRow(i++).createCell(j).setCellValue("${note.endDay} / ${note.endMon}/${note.endYear}")
                    sheet.getRow(i++).createCell(j).setCellValue(note.place)
                    sheet.getRow(i++).createCell(j).setCellValue(note.people)
                    sheet.getRow(i).createCell(j).setCellValue(note.natureOfWork)
                    i=0
                    j++
                }
            val date = Calendar.getInstance().timeInMillis.toString()
            val fileName:String = "$FileN$date.xlsx"
            val extStorageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val folder = File(extStorageDirectory, "Custom Logs/$FolderN")
            folder.mkdirs()
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
        }

        client_selected.setOnClickListener {
            Client = spinnerclient_CW.selectedItem.toString()
            launch {
                context?.let {
                    clientdata=NoteDatabase(it).getNoteDao().getClientLog(Client)
                    it.toast(Client+"has been selected")

                }
            }
        }


        client_generate.setOnClickListener {
            try {
                customGenerator(clientdata,"Client Log",Client)
                context?.toast(Client+"'s Report has been generated")
            }catch (e:UninitializedPropertyAccessException) {
                val errorText = spinnerclient_CW.selectedView as TextView
                errorText.error = "confirm client"
                context?.toast("Please confirm client")
                return@setOnClickListener
            }
        }
        project_selected.setOnClickListener {
            Project = spinnerproject_CW.selectedItem.toString()
            launch {
                context?.let {
                    projectdata = NoteDatabase(it).getNoteDao().getProjectLog(Project)
                    it.toast(Project+" has been selected")
                }
            }
        }
        project_generate.setOnClickListener {
            try {
                customGenerator(projectdata,"Project Log",Project)
                context?.toast(Project+"'s Report has been generated ")
            }
            catch (e:UninitializedPropertyAccessException) {
                val errorText = spinnerproject_CW.selectedView as TextView
                errorText.error = "confirm project"
                context?.toast("Please confirm project")
                return@setOnClickListener
            }
        }


//        var FrDate: String = ""
//        from_date_pick.setOnClickListener {
//            val cal = Calendar.getInstance()
//            fun updateFromDate(): String {
//                val myFormat = "dd/MM/yyyy" // mention the format you need
//                val sdf = SimpleDateFormat(myFormat, Locale.US)
//                return sdf.format(cal.getTime())
//            }
//            val dateSetListener =
//                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//                    cal.set(Calendar.YEAR, year)
//                    cal.set(Calendar.MONTH, monthOfYear)
//                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                    FrDate = updateFromDate()
//                    context?.toast(FrDate)
//                }
//            context?.let {
//                DatePickerDialog(
//                    it,
//                    dateSetListener, // set DatePickerDialog to point to today's date when it loads up
//                    cal.get(Calendar.YEAR),
//                    cal.get(Calendar.MONTH),
//                    cal.get(Calendar.DAY_OF_MONTH)
//                ).show()
//            }
//
//        }
//        var ToDate: String = ""
//        to_date_pick.setOnClickListener {
//            val cal = Calendar.getInstance()
//            fun updateToDate(): String {
//                val myFormat = "dd/MM/yyyy" // mention the format you need
//                val sdf = SimpleDateFormat(myFormat, Locale.US)
//                return sdf.format(cal.getTime())
//            }
//            val dateSetListener2 =
//                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//                    cal.set(Calendar.YEAR, year)
//                    cal.set(Calendar.MONTH, monthOfYear)
//                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                    ToDate = updateToDate()
//                    context?.toast(ToDate)
//                }
//            context?.let {
//                DatePickerDialog(
//                    it,
//                    dateSetListener2, // set DatePickerDialog to point to today's date when it loads up
//                    cal.get(Calendar.YEAR),
//                    cal.get(Calendar.MONTH),
//                    cal.get(Calendar.DAY_OF_MONTH)
//                ).show()
//            }
//
//        }
//        date_selected.setOnClickListener {
//            if (ToDate.isEmpty()){
//                context?.toast("Select to Date")
//                return@setOnClickListener
//            }
//            if(FrDate.isEmpty()){
//                context?.toast("Select from Date")
//                return@setOnClickListener
//            }
//        }
    }

}
