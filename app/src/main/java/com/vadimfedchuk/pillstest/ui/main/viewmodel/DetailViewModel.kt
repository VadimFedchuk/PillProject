package com.vadimfedchuk.pillstest.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vadimfedchuk.pillstest.ui.main.RepositoryPills
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryPills: RepositoryPills
    private val pill: MutableLiveData<Pills>

    init {
        repositoryPills = RepositoryPills(application)
        pill = MutableLiveData()
    }

    fun getPills(id: Int):LiveData<Pills> {
        repositoryPills.getPillById(id) {
            pill.value = it
        }
        return pill
    }
}