package com.example.solidpractice.service

import android.util.Log

interface Database {
    fun save(data: String)
}

class MySQLDatabase: Database{
    override fun save(data: String) {
        Log.d("checkdata", "MySQLDatabase: save :$data")
    }

}