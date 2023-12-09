package com.alysa.taskmodule.data

class Data {
    private var id: String? = null
    private var date: String? = null
    private var notes: String? = null

    constructor()

    constructor(id: String?, date: String?, notes: String?) {
        this.id = id
        this.date = date
        this.notes = notes
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getNotes(): String? {
        return notes
    }

    fun setNotes(notes: String?) {
        this.notes = notes
    }
}
