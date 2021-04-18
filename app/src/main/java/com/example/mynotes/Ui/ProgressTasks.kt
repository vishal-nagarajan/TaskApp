package com.example.mynotes.Ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_progress_tasks.*
import kotlinx.android.synthetic.main.fragment_task_in_progress.*
import kotlinx.coroutines.launch

class ProgressTasks : BaseFragment() {
    var a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            a = ProgressTasksArgs.fromBundle(it).page
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_tasks, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view_progress_task.setHasFixedSize(true)
        recycler_view_progress_task.layoutManager =  StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        if(a==2){
            progress_title.text = "Completed Tasks"
            launch {
                context?.let {
                    val note = NoteDatabase(it).getNoteDao().getCompletedTasks()
                    recycler_view_progress_task.adapter =
                        ProgressTasksAdapter2(note)
                }
            }
        }else if(a==1){

            launch {
                context?.let {
                    val note = NoteDatabase(it).getNoteDao().getProgressTasks()
                    val timelogData=NoteDatabase(it).getNoteDao().getTaskWithTimeLog()
                    recycler_view_progress_task.adapter =
                        ProgressTasksAdapter(note,timelogData)
                }
            }
        }else if(a==3){
            progress_title.text = "Tasks Over Due"
            launch {
                context?.let {
                    val note = NoteDatabase(it).getNoteDao().getOverDueTasks()
                    recycler_view_progress_task.adapter =
                        DueOverAdapter(note)
                }
            }
        }else if (a==4){
            progress_title.text = "Tasks yet to be due"
            launch {
                context?.let {
                    val note = NoteDatabase(it).getNoteDao().getYetToDueTasks()
                    recycler_view_progress_task.adapter =
                        YetDueAdapter(note)
                }
            }

        }
    }
}