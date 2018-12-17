package com.example.owner.read_record

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ReadRecord :RealmObject(){
    @PrimaryKey
    var id:Long=0
    var title:String=""
    var author:String=""
    var isbn:String=""
    var comment:String=""
}