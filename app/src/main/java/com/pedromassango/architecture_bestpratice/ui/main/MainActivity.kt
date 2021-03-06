package com.pedromassango.architecture_bestpratice.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pedromassango.architecture_bestpratice.data.models.Phrase
import com.pedromassango.architecture_bestpratice.R
import com.pedromassango.architecture_bestpratice.ui.MyAdpter
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    // setup the ViewModel
    private val mainViewModel: MainViewModel by viewModel()

    private val adapter: MyAdpter by lazy{
        MyAdpter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // inialize my recyclerView
        recycler_view.adapter = adapter

        // listen for changes  and update the UI
        mainViewModel.getAllPhrases().observe(this, Observer{

            if(it!!.isEmpty()){
                tv_phrases_count.text = "No data."
                return@Observer
            }


            adapter.addAll( it)
        })

        // load all data
        mainViewModel.loadAllData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(R.id.action_add == item!!.itemId){
            showDialogNewWord()
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Show a input dialog to type a phrase
     * and add it in database
     */
    private fun showDialogNewWord() {
        val edtPhrase = EditText(this)

        AlertDialog.Builder(this)
                .setTitle("Type a phrase")
                .setView(edtPhrase)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save") { dialogInterface, i ->
                    val text = edtPhrase.text.toString()

                    // if there is a text, save it
                    if (!text.trim { it <= ' ' }.isEmpty()) {
                        val phrase = Phrase(text = text)

                        //TODO: save in room database
                    }
                }
                .create()
                .show() // show dialog
    }


}
