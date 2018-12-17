package com.example.owner.read_record

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        realm=Realm.getDefaultInstance()
        val readRecords=realm.where<ReadRecord>().findAll()
        listView.adapter=ReadRecordAdapter(readRecords)

        fab.setOnClickListener { view ->
           startActivity<ReadRecordEditActivity>()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val readRecord=parent.getItemAtPosition(position) as ReadRecord
            startActivity<ReadRecordEditActivity>(
                "read_record_id" to readRecord.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
