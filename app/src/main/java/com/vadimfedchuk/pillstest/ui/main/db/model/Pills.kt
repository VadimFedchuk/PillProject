package com.vadimfedchuk.pillstest.ui.main.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Pills (

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idPill: Int? = null,
    val labelTrade: String? = null,
    val nameManufacturer: String? = null,
    val packagingDescription: String? = null,
    val innId: Int? = null,
    val innName: String? = null,
    val pharmFormId: Int? = null,
    val pharmFormName: String? = null,
    val compositionDescription: String? = null

)
