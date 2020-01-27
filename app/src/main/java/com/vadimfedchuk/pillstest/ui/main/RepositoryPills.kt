package com.vadimfedchuk.pillstest.ui.main

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.vadimfedchuk.pillstest.ui.main.db.PillsDao
import com.vadimfedchuk.pillstest.ui.main.db.PillsDatabase
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills
import com.vadimfedchuk.pillstest.ui.main.network.ApiClient
import com.vadimfedchuk.pillstest.ui.main.network.ApiService
import com.vadimfedchuk.pillstest.ui.main.pojo.PillsResponse
import com.vadimfedchuk.pillstest.ui.main.pojo.ResultsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class RepositoryPills(val context: Context) {

    private val database: PillsDatabase
    private val apiService: ApiService
    private var pageNumberNext:Int? = null

    init {
        database = PillsDatabase.getAppDatabase(context)
        apiService = ApiClient.getClient(context).create(ApiService::class.java)
    }

    val sizePills: Boolean
        get() = database.pillsDao.lastPills != null //если true, то бд не пустая

    fun getAllPills(callback:(data:ArrayList<Pills>?, isSuccess: Boolean) -> Unit) {
        val list = ArrayList<Pills>()
        apiService.allPills
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<PillsResponse>() {

                override fun onSuccess(response: PillsResponse) {
                    response.results?.forEach {
                        list.add(convertPillResponseToPill(it!!))
                    }
                    if(list.size == response.results?.size) {
                        callback(list, true)
                    }
                    pageNumberNext = response.next?.substringAfter("=")?.toInt()
                    deleteAllData()
                    setPills(list)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, "error getAllPills $e", Toast.LENGTH_SHORT).show()
                    if(sizePills) {
                        list.addAll(database.pillsDao.allPills)
                        callback(list, true)
                    } else {
                        callback(null, false)
                    }
                }
            })
    }

    fun getAllPillsByQuery(query: String, callback:(data:ArrayList<Pills>?, isSuccess: Boolean) -> Unit) {
        val list = ArrayList<Pills>()
        apiService.getPillsByQuery(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<PillsResponse>() {

                override fun onSuccess(response: PillsResponse) {
                    response.results?.forEach {
                        list.add(convertPillResponseToPill(it!!))
                    }
                    if(list.size == response.results?.size) {
                        callback(list, true)
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, "error getAllPillsByQuery $e", Toast.LENGTH_SHORT).show()
                        callback(null, false)
                    }

            })
    }

    fun loadMorePills(callback:(data:ArrayList<Pills>?, isSuccess: Boolean) -> Unit) {
        val list = ArrayList<Pills>()
        if(pageNumberNext != null) {
            apiService.loadMorePills(pageNumberNext!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : DisposableSingleObserver<PillsResponse>() {

                    override fun onSuccess(response: PillsResponse) {
                        response.results?.forEach {
                            list.add(convertPillResponseToPill(it!!))
                        }
                        pageNumberNext = response.next?.substringAfter("=")?.toInt()
                        if (list.size == response.results?.size) {
                            callback(list, true)
                        }

                        setPills(list)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, "loadMorePills $e", Toast.LENGTH_SHORT).show()
                        callback(list, false)
                    }

                })
        }
    }

    fun getPillById(id: Int, callback:(data:Pills) -> Unit) {
        var pill:Pills
        apiService.getPillById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<ResultsItem>() {
                override fun onSuccess(response: ResultsItem) {
                    pill = convertPillResponseToPill(response)
                    callback(pill)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, "error getAllPills $e", Toast.LENGTH_SHORT).show()
                    pill = database.pillsDao.getPill(id)
                    callback(pill)

                }
            })
    }

    fun deleteAllData() {
        database.pillsDao.deleteAllPills()
    }

    fun setPills(list: List<Pills>) {
        InsertAsyncTask(database.pillsDao).execute(list)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: PillsDao) :
        AsyncTask<List<Pills>, Void, Void>() {

        override fun doInBackground(vararg params: List<Pills>): Void? {

            mAsyncTaskDao.setPills(params[0])
            return null
        }
    }

    private fun convertPillResponseToPill(pill: ResultsItem) = Pills(
        0, pill.id,
        pill.tradeLabel?.name ?: "no tradeLabel name",
        pill.manufacturer?.name  ?: "no manufacturer name",
        pill.packaging?.description  ?: "no packaging description",
        pill.composition?.inn?.id ?: 0,
        pill.composition?.inn?.name ?: "no inn name",
        pill.composition?.pharmForm?.id ?: 0,
        pill.composition?.pharmForm?.name ?: "no pharmForm name",
        pill.composition?.description ?: "no composition description"
    )
}