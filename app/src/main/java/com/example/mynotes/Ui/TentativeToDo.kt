package com.example.mynotes.Ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_tentative_to_do.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class TentativeToDo : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tentative_to_do, container, false)
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
        recycler_view_tentative.setHasFixedSize(true)
        recycler_view_tentative.layoutManager =  StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        launch {
            context?.let {
                val note = NoteDatabase(it).getNoteDao().getAllNotes()
//                if(note.sta)
                recycler_view_tentative.adapter =
                    TentativeAdapter(note)
            }
        }
        //        modify_button.setOnClickListener{
//            val action = HomeFragmentDirections.actionAddNote()
//            Navigation.findNavController(it).navigate(action)
//        }
//        button_menu.setOnClickListener{
//            val action = HomeFragmentDirections.actionMenu()
//            Navigation.findNavController(it).navigate(action)
//        }
    }
}