package com.alysa.taskmodule.ui.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alysa.taskmodule.DBhelper.dbHelper
import com.alysa.taskmodule.R
import com.alysa.taskmodule.adapter.Adapter
import com.alysa.taskmodule.data.Data

class NoteFragment : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: Adapter
    private lateinit var SQLite: dbHelper
    private val itemList: MutableList<Data> = ArrayList()

    companion object {
        const val TAG_ID = "id"
        const val TAG_DATE = "date"
        const val TAG_NOTES = "notes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SQLite = dbHelper(this)
        recyclerView = findViewById(R.id.listview)
        adapter = Adapter(this, itemList)
        recyclerView?.adapter = adapter

        getAllData()
    }

    private fun getAllData() {
        val row: ArrayList<HashMap<String, String>> = SQLite.getAllData()
        for (i in row.indices) {
            val id = row[i][TAG_ID]
            val poster = row[i][TAG_DATE]
            val title = row[i][TAG_NOTES]
            val data = Data()
            data.setId(id)
            data.setDate(poster)
            data.setNotes(title)
            itemList.add(data)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        itemList.clear()
        getAllData()
    }
}
