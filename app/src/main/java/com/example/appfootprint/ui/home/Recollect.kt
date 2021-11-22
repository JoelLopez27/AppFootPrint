package com.example.appfootprint.ui.home

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Recollect(@get: Exclude var id: String = "",
                     var name: String = "",
                     var title: String = "",
                     var date: String = "",
                     var photoUrl: String = "",
                     var cantMaterial: Double = 0.0,
                     var typeMaterial: String = "",
                     var cantC02: Double = 0.0,
                     var likeList: Map<String, Boolean> = mutableMapOf())
