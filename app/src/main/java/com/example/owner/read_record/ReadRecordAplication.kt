package com.example.owner.read_record

import android.app.Application
import io.realm.Realm

class ReadRecordAplication :Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}