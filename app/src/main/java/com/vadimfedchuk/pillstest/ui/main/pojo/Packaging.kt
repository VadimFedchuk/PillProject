package com.vadimfedchuk.pillstest.ui.main.pojo


import com.google.gson.annotations.SerializedName


data class Packaging(

    @field:SerializedName("in_bulk")
	val inBulk: Boolean? = null,

    @field:SerializedName("minimal_quantity")
	val minimalQuantity: String? = null,

    @field:SerializedName("package_quantity")
	val packageQuantity: String? = null,

    @field:SerializedName("composition")
	val composition: Composition? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("id")
	val id: Int? = null
)