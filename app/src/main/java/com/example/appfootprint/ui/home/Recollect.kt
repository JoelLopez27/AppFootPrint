package com.example.appfootprint.ui.home

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Recollect(@get: Exclude var id: String = "",
                     var name: String = "",
                     var title: String = "",
                     var date: String = "",
                     var photoUrl: String = "",
                     var cantMaterial: String = "",
                     var typeMaterial: String = "",
                     var cantC02: String = "",
                     var likeList: Map<String, Boolean> = mutableMapOf())
