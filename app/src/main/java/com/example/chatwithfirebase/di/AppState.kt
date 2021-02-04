package com.example.chatwithfirebase.di

import java.util.*

object AppState {

    private var status = false

    fun getStatus(): Boolean {
        return status
    }

    fun setStatus(statusUser : Boolean) {
        status = statusUser
    }

}