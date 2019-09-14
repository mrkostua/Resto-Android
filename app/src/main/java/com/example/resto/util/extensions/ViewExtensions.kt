package com.example.resto.util.extensions

import android.view.View
import androidx.core.content.ContextCompat

fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}