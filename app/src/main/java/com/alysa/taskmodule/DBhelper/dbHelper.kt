package com.alysa.taskmodule.DBhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class dbHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_DATE, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_DATE = "module.db"
        const val TABLE_SQLite = "sqlite"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_NOTES = "notes"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_MOVIE_TABLE = "CREATE TABLE $TABLE_SQLite (" +
                "$COLUMN_ID INTEGER PRIMARY KEY autoincrement, " +
                "$COLUMN_DATE TEXT NOT NULL, " +
                "$COLUMN_NOTES TEXT NOT NULL" +
                " )"
        db.execSQL(SQL_CREATE_MOVIE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SQLite")
        onCreate(db)
    }

    fun getAllData(): ArrayList<HashMap<String, String>> {
        val wordList = ArrayList<HashMap<String, String>>()
        val selectQuery = "SELECT * FROM $TABLE_SQLite"
        val database: SQLiteDatabase = writableDatabase
        val cursor = database.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val map = HashMap<String, String>()
                map[COLUMN_ID] = cursor.getString(0)
                map[COLUMN_DATE] = cursor.getString(1)
                map[COLUMN_NOTES] = cursor.getString(2)
                wordList.add(map)
            } while (cursor.moveToNext())
        }
        Log.e("select sqlite ", "" + wordList)
        cursor.close()
        return wordList
    }

    fun insert(date: String, notes: String) {
        val database: SQLiteDatabase = writableDatabase
        val queryValues =
            "INSERT INTO $TABLE_SQLite ($COLUMN_DATE,$COLUMN_NOTES) VALUES ('$date', '$notes')"
        Log.e("insert sqlite ", "" + queryValues)
        database.execSQL(queryValues)
        database.close()
    }

    fun update(id: Int, date: String, notes: String) {
        val database: SQLiteDatabase = writableDatabase
        val updateQuery = ("UPDATE $TABLE_SQLite SET " +
                "$COLUMN_DATE='$date', " +
                "$COLUMN_NOTES='$notes'" +
                " WHERE $COLUMN_ID='$id'")
        Log.e("update sqlite ", updateQuery)
        database.execSQL(updateQuery)
        database.close()
    }

    fun delete(id: Int) {
        val database: SQLiteDatabase = writableDatabase
        val updateQuery = "DELETE FROM $TABLE_SQLite WHERE $COLUMN_ID='$id'"
        Log.e("update sqlite ", updateQuery)
        database.execSQL(updateQuery)
        database.close()
    }
}
