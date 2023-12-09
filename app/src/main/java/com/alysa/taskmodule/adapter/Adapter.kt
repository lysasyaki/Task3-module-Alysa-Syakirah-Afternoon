package com.alysa.taskmodule.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alysa.taskmodule.R
import com.alysa.taskmodule.data.Data
import com.alysa.taskmodule.DBhelper.dbHelper
import com.alysa.taskmodule.ui.add_note.AddNoteFragment

class Adapter(private val activity: Activity, private val items: List<Data>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null
    private var dbHelper: dbHelper? = null
    private val TAG_ID = "id"
    private val TAG_DATE = "date"
    private val TAG_NOTES = "notes"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val view = inflater!!.inflate(R.layout.note_item, parent, false)
        dbHelper = dbHelper(activity)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Data = items[position]

        holder.id.text = data.getId().toString()
        holder.date.text = data.getDate()
        holder.notes.text = data.getNotes()

        val currentActivity = activity

        holder.itemView.setOnLongClickListener {
            val idx: String = data.getId().toString()
            val date: String? = data.getDate()
            val notes: String? = data.getNotes()

            val dialogItems = arrayOf<CharSequence>("Edit", "Delete")
            androidx.appcompat.app.AlertDialog.Builder(currentActivity)
                .setCancelable(true)
                .setItems(dialogItems) { _, which ->
                    when (which) {
                        0 -> {
                            val intent = Intent(currentActivity, AddNoteFragment::class.java)
                            intent.putExtra(TAG_ID, idx)
                            intent.putExtra(TAG_DATE, date)
                            intent.putExtra(TAG_NOTES, notes)
                            currentActivity.startActivity(intent)
                        }

                        1 -> {
                            dbHelper?.delete(idx.toInt())
                            items.toMutableList().removeAt(position)
                            notifyDataSetChanged()
                        }
                    }
                }
                .show()

            true
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.txtId)
        val date: TextView = itemView.findViewById(R.id.tanggal)
        val notes: TextView = itemView.findViewById(R.id.note)
    }
}
