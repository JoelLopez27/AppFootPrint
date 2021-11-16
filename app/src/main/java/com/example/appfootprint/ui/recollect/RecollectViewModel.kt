package com.example.appfootprint.ui.recollect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfootprint.db.UserRecollect
import com.example.appfootprint.repository.RecollectRepository
import kotlinx.coroutines.launch

class RecollectViewModel(val recollectRepository: RecollectRepository) : ViewModel() {


   // fun getUpdateList(): LiveData<MutableList<UserRecollect>> = recollectRepository.getSavedRecollect()

    fun deleteRecollectionData(userRecollect: UserRecollect) {
        viewModelScope.launch {
            val result: Int = recollectRepository.deleteRecollect(userRecollect)
            if (result == 0)
                throw IllegalAccessException("Invalid Recollect to delete")
        }
    }
}