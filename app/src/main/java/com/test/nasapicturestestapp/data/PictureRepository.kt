package com.test.nasapicturestestapp.data

import android.app.Application
import com.google.gson.Gson
import com.test.nasapicturestestapp.utils.OpenForTesting
import com.test.nasapicturestestapp.utils.getAssetJsonData
import com.test.nasapicturestestapp.utils.toNasaPicturesList
import java.lang.Exception

@OpenForTesting
class PictureRepository(private val application: Application, private val gson: Gson, private val fileName: String) {

    /**
     * Mimicking network response here with success and error callback
     */
    fun getPicturesList(
        success: ((List<NasaPicture>) -> Unit)? = null,
        error: ((Exception) -> Unit)? = null
    ) {
        val jsonArray = application.getAssetJsonData(fileName)
        val list = jsonArray?.toNasaPicturesList(gson)
        list?.let { it ->
            val arrayList = ArrayList(it)
            arrayList.sortByDescending { picture -> picture.date }
            success?.invoke(arrayList)
        } ?: kotlin.run {
            error?.invoke(Exception())
        }
    }
}