package com.example.resto.util.config

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes stringId: Int): String
    fun getString(@StringRes stringId: Int, param: String): String
    fun getStringArray(@ArrayRes stringArrayId: Int): Array<String>
}