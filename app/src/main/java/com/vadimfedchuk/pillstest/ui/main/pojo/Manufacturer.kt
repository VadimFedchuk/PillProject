package com.vadimfedchuk.pillstest.ui.main.pojo

import com.google.gson.annotations.SerializedName

data class Manufacturer(

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("id")
	val id: Int? = null
)