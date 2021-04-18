package com.example.mynotes.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mynotes.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      getSupportActionBar().hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
//        val navHostFragment = fragment as NavHostFragment
//        val graphInflater = navHostFragment.navController.navInflater
//        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
//        val navController = navHostFragment.navController
//        val i=true
//        if(i){
//            navGraph.startDestination=R.id.tentativeToDo
//        }else{
//            navGraph.startDestination=R.id.taskinProgress
//        }
//        navController.graph=navGraph
    }

//    back navigation
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.fragment),null)

    }
}