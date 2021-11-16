package com.example.appfootprint.ui.recollect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfootprint.db.UserRecollect
import com.example.appfootprint.repository.RecollectRepository
import kotlinx.coroutines.launch

class AddRecollectViewModel(val recollectRepository: RecollectRepository): ViewModel() {

  fun calculateRecollect(cantMaterial: String, material: String): String{

      val cantKG: Float = cantMaterial.toFloat()
      val totalKg : String

        when (material) {
           "Aluminio" -> {
               totalKg = "%.1f".format(cantKG * 9)
            }
            "Papel" -> {
                totalKg = "%.1f".format(cantKG * 0.9)
            }
            "Cobre" -> {
                totalKg = "%.1f".format(cantKG * 2.77)
            }
            "Pilas o Baterias" -> {
                totalKg = "%.1f".format(cantKG * 0.07)
            }
            "Envases Metalicos" -> {
                totalKg = "%.1f".format(cantKG * 7)
            }
            "Aparatos electrónicos" -> {
                totalKg = "%.1f".format(cantKG * 14)
            }
            "Textiles" -> {
                totalKg = "%.1f".format(cantKG * 3.6)
            }
            "Plastico" -> {
                totalKg = "%.1f".format(cantKG * 2.70)
            }
            else -> {
                return (0).toString()
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


}