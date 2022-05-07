package com.test.nasapicturestestapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.nasapicturestestapp.R
import com.test.nasapicturestestapp.data.PictureRepository
import com.test.nasapicturestestapp.utils.Success
import com.test.nasapicturestestapp.utils.UiState
import com.test.nasapicturestestapp.utils.Error


class DetailsViewModel(private val pictureRepository: PictureRepository) : ViewModel() {

    val picturesListState = MutableLiveData<UiState>()

    fun getPicturesList() {
        pictureRepository.getPicturesList({
            picturesListState.postValue(Success(it))
        }, {
            picturesListState.postValue(Error(R.string.default_error_msg))
        })
    }
}