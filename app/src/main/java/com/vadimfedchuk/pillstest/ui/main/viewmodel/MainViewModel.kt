package com.vadimfedchuk.pillstest.ui.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vadimfedchuk.pillstest.ui.main.RepositoryPills
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var isLoadData = MutableLiveData<Boolean>()
        private set

    var listPills: MutableLiveData<List<Pills>>? = null
        private set

    private val repositoryPills: RepositoryPills

    init {
        listPills = MutableLiveData()
        repositoryPills = RepositoryPills(application)

        repositoryPills.getAllPills(callback = { data, isSuccess ->
            if(isSuccess) {

                listPills!!.postValue(data)
            }
            isLoadData.setValue(isSuccess)
        })
    }

    fun refreshData() {
        repositoryPills.getAllPills { data, isSuccess ->
            if(isSuccess) {
                listPills!!.value = arrayListOf()
                listPills!!.value = data
            }
            isLoadData.setValue(isSuccess)
        }
    }

    fun loadMorePills() {
        repositoryPills.loadMorePills { data, isSuccess ->
            isLoadData.value = isSuccess
            listPills!!.value =data!!
        }
    }

    fun getPillsByQuery(query: String) {
        repositoryPills.getAllPillsByQuery(query, callback = {data, isSuccess ->
            if(isSuccess) {
                listPills!!.postValue(data)
            } else {
                val list = listPills!!.value
                listPills!!.value = arrayListOf()
                listPills!!.value = list!!.filter{it.labelTrade!!.contains(query, false)}
            }
        })
    }
}
