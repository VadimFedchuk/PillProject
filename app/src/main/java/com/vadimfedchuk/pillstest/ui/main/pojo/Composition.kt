package com.vadimfedchuk.pillstest.ui.main.pojo

import com.google.gson.annotations.SerializedName

data class Composition(

    @field:SerializedName("atc")
	val atc: List<String?>? = null,

    @field:SerializedName("dosage")
	val dosage: Double? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("inn")
    val inn: Inn? = null,

    @field:SerializedName("pharm_form")
    val pharmForm: PharmForm? = null
)