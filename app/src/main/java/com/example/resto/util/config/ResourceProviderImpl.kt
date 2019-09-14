package com.example.resto.util.config

import android.content.Context
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(private val context: Context) : ResourceProvider {

    override fun getString(stringId: Int): String = context.getString(stringId)

    override fun getString(stringId: Int, param: String): String = context.getString(stringId, param)

    override fun getStringArray(stringArrayId: Int): Array<String> = context.resources.getStringArray(stringArrayId)

}