package com.test.nasapicturestestapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.nasapicturestestapp.R
import com.test.nasapicturestestapp.data.PictureRepository
import com.test.nasapicturestestapp.utils.Success
import com.test.nasapicturestestapp.utils.UiState
import com.test.nasapicturestestapp.utils.Error
import com.test.nasapicturestestapp.utils.OpenForTesting


@OpenForTesting
class MainViewModel(private val pictureRepository: PictureRepository) : ViewModel() {

    val picturesListState = MutableLiveData<UiState>()

    fun getPicturesList() {
        pictureRepository.getPicturesList({
            picturesListState.postValue(Success(it))
        }, {
            picturesListState.postValue(Error(R.string.default_error_msg))
        })
    }
}