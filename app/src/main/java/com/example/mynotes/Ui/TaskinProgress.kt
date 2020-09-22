package com.example.mynotes.Ui



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_task_in_progress.*
import kotlinx.android.synthetic.main.fragment_tentative_to_do.*
import kotlinx.android.synthetic.main.fragment_tentative_to_do.todays_date
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class TaskinProgress : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_in_progress, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val date = SimpleDateFormat("dd/MM/yyyy")
        val currentDate=date.format(Date())
        val todayDate=currentDate.split("/")
        val day=Integer.parseInt(todayDate.elementAt(0))
        val mon=Integer.parseInt(todayDate.elementAt(1))
        val year=Integer.parseInt(todayDate.elementAt(2))
        todays_date.text=currentDate
        recycler_view_taskinprogress.setHasFixedSize(true)
        recycler_view_taskinprogress.layoutManager =  StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        launch {
            context?.let {
                val note = NoteDatabase(it).getNoteDao().getTodayNotes()
                recycler_view_taskinprogress.adapter =
                    TaskinProgressAdapter(note)
            }
        }
        mainmenu.setOnClickListener {
            val action  = TaskinProgressDirections.actionTaskinProgressToMainMenu()
            Navigation.findNavController(it).navigate(action)
        }
    }
}