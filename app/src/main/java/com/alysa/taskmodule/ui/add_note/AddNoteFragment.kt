package com.alysa.taskmodule.ui.add_note

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alysa.taskmodule.R
import com.alysa.taskmodule.DBhelper.dbHelper
import com.alysa.taskmodule.ui.note.NoteFragment

class AddNoteFragment : AppCompatActivity() {

    private var txtid: EditText? = null
    private var ETdate: EditText? = null
    private var ETnote: EditText? = null
    private var BMsubmit: Button? = null
    private var BMcancel: Button? = null
    private val SQLite = dbHelper(this)
    private var id: String? = null
    private var date: String? = null
    private var notes: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txtid = findViewById(R.id.txtId)
        ETdate = findViewById(R.id.ETdate)
        ETnote = findViewById(R.id.ETnote)
        BMsubmit = findViewById(R.id.MBsubmit)
        BMcancel = findViewById(R.id.MBcancel)

        id = intent.getStringExtra(NoteFragment.TAG_ID)
        date = intent.getStringExtra(NoteFragment.TAG_DATE)
        notes = intent.getStringExtra(NoteFragment.TAG_NOTES)

        if (id == null || id == "") {
            title = "Add Data"
        } else {
            title = "Edit Data"
            txtid?.setText(id)
            ETdate?.setText(date)
            ETnote?.setText(notes)
        }

        BMsubmit?.setOnClickListener {
            try {
                if (txtid?.text.toString() == "") {
                    save()
                } else {
                    edit()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        BMcancel?.setOnClickListener {
            blank()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                blank()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun blank() {
        ETdate?.requestFocus()
        txtid?.setText(null)
        ETdate?.setText(null)
        ETnote?.setText(null)
    }

    private fun save() {
        if (ETdate?.text.toString().isEmpty() || ETnote?.text.toString().isEmpty()) {
            Toast.makeText(this,"Please input date or notes ....", Toast.LENGTH_SHORT).show()
        } else {
            SQLite.insert(
                ETdate?.text.toString().trim(),
                ETnote?.text.toString().trim()
            )
            Toast.makeText(this,"Notes already save", Toast.LENGTH_SHORT).show()
            blank()
            finish()
        }
    }

    private fun edit() {
        if (ETdate?.text.toString().isEmpty() || ETnote?.text.toString().isEmpty()) {
            Toast.makeText(this,"Please input date or notes ....", Toast.LENGTH_SHORT).show()
        } else {
            SQLite.update(
                txtid?.text.toString().trim().toInt(),
                ETdate?.text.toString().trim(),
                ETnote?.text.toString().trim()
            )
            Toast.makeText(this,"Notes already edited", Toast.LENGTH_SHORT).show()
            blank()
            finish()
        }
    }
}
