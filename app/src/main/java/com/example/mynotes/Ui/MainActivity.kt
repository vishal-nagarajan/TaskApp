package com.example.mynotes.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.internal.view.SupportMenu
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mynotes.R
import com.example.mynotes.Ui.ClientDialogFragment.ClientDialogListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      getSupportActionBar().hide()
//        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }


//    back navigation
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.fragment),null)

    }
}