package com.example.appfootprint.ui.recollect

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfootprint.db.UserRecollect
import com.example.appfootprint.repository.RecollectRepository
import kotlinx.coroutines.launch

class AddRecollectViewModel(val recollectRepository: RecollectRepository): ViewModel() {

  fun calculateRecollect(cantMaterial: Double, material: String): Double{

      val totalKg : Double

        when (material) {
           "Aluminio" -> {
               totalKg = ("%.1f".format(cantMaterial * 9)).toDouble()
            }
            "Papel" -> {
                totalKg = ("%.1f".format(cantMaterial * 0.9)).toDouble()
            }
            "Cobre" -> {
                totalKg = ("%.1f".format(cantMaterial * 2.77)).toDouble()
            }
            "Pilas o Baterias" -> {
                totalKg = ("%.1f".format(cantMaterial * 0.07)).toDouble()
            }
            "Envases Metalicos" -> {
                totalKg = ("%.1f".format(cantMaterial * 7)).toDouble()
            }
            "Aparatos electrónicos" -> {
                totalKg = ("%.1f".format(cantMaterial * 14)).toDouble()
            }
            "Textiles" -> {
                totalKg = ("%.1f".format(cantMaterial * 3.6)).toDouble()
            }
            "Plastico" -> {
                totalKg = ("%.1f".format(cantMaterial * 2.70)).toDouble()
            }
            else -> {
                return (0).toDouble()
            }
        }
            return totalKg
    }

    fun insertRecollectData(userRecollect: UserRecollect) {
        viewModelScope.launch {
            recollectRepository.insertUserRecollectData(userRecollect)
        }
    }

    fun updateRecollectData(userRecollect: UserRecollect) {
        viewModelScope.launch {
            recollectRepository.updateUserRecollectData(userRecollect)
        }
    }

    fun fetchRecollectData(id: Int) {
        viewModelScope.launch {
            recollectRepository.getUserRecollectData(id)
        }
    }

   fun getSavedRecollect() = recollectRepository.getSavedRecollect()


    fun deleteRecollectionData(userRecollect: UserRecollect) {
        viewModelScope.launch {
            recollectRepository.deleteRecollect(userRecollect)
        }
    }

    fun getCountMaterial(): LiveData<Double> = recollectRepository.getTotalMaterial()

    fun getCountKgCo2(): LiveData<Double> = recollectRepository.getTotalKgCo2()

    fun getSizeRows(): LiveData<Int> = recollectRepository.getSizeRows()

    fun getDate(): LiveData<String> = recollectRepository.getDate()

}