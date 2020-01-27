package com.vadimfedchuk.pillstest.ui.main.pojo


import com.google.gson.annotations.SerializedName

data class ResultsItem(

    @field:SerializedName("trade_label")
	val tradeLabel: TradeLabel? = null,

    @field:SerializedName("code")
	val code: String? = null,

    @field:SerializedName("composition")
	val composition: Composition? = null,

    @field:SerializedName("packaging")
	val packaging: Packaging? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("manufacturer")
	val manufacturer: Manufacturer? = null
)