package com.example.mynotes.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mynotes.R
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenu : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        add_task.setOnClickListener{
            val action = MainMenuDirections.mAddNote()
            Navigation.findNavController(it).navigate(action)
        }
        edit_task.setOnClickListener{
            val action = MainMenuDirections.actionTaskInProgress()
            Navigation.findNavController(it).navigate(action)
        }
        tasks_yet_to_be_due.setOnClickListener{
           val action = MainMenuDirections.actionDueYet()
//           actionTentative()
            Navigation.findNavController(it).navigate(action)
        }
        tasks_in_progress.setOnClickListener {
            val action =MainMenuDirections.actionStatusinProgress()
            Navigation.findNavController(it).navigate(action)
        }
        button_work_log.setOnClickListener {
            val action = MainMenuDirections.actionWorkLogFrahment()
            Navigation.findNavController(it).navigate(action)
        }
        completed_tasks.setOnClickListener {
            val action = MainMenuDirections.actionStatusinCompleted()
            Navigation.findNavController(it).navigate(action)
        }
        tasks_over_due.setOnClickListener {
            val action =MainMenuDirections.actionDueOver()
            Navigation.findNavController(it).navigate(action)
        }
        button_custom_work_log.setOnClickListener {
            val action  = MainMenuDirections.actionCustomWorkLog()
            Navigation.findNavController(it).navigate(action)
        }


    }

}