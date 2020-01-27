package com.vadimfedchuk.pillstest.ui.main.pojo

import com.google.gson.annotations.SerializedName

data class Inn(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)