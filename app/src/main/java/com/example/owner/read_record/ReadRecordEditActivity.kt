package com.example.owner.read_record

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresPermission
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_read_record_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ReadRecordEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_record_edit)
        realm= Realm.getDefaultInstance()

        val ReadRecordId=intent?.getLongExtra("read_record_id",-1L)
        if(ReadRecordId!=-1L) {
            val readRecord = realm.where<ReadRecord>().equalTo("id", ReadRecordId).findFirst()
            titleEdit.setText(readRecord?.title)
            authorEdit.setText(readRecord?.author)
            isbnEdit.setText(readRecord?.isbn)
            commentEdit.setText(readRecord?.comment)
            delete.visibility = View.VISIBLE
        }else{
            delete.visibility=View.INVISIBLE
        }

        save.setOnClickListener {
            when (ReadRecordId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<ReadRecord>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val ReadRecord = realm.createObject<ReadRecord>(nextId)
                        ReadRecord.title = titleEdit.text.toString()
                        ReadRecord.author = authorEdit.text.toString()
                        ReadRecord.isbn = isbnEdit.text.toString()
                        ReadRecord.comment = commentEdit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val readRecord = realm.where<ReadRecord>().equalTo("id", ReadRecordId).findFirst()
                        readRecord?.title = titleEdit.text.toString()
                        readRecord?.author = authorEdit.text.toString()
                        readRecord?.isbn = isbnEdit.text.toString()
                        readRecord?.comment = commentEdit.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        delete.setOnClickListener {
            realm.executeTransaction {
                realm.where<ReadRecord>().equalTo("id",ReadRecordId)?.findFirst()?.deleteFromRealm()
            }
            alert ("削除しました"){
                yesButton { finish() }
            }.show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
