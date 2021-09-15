package com.hadiid.assunnah.ui.module

import com.hadiid.assunnah.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPresenter(
    private val view: DetailView,
    private val api: ApiService

) {

    init {
        view.setupListener()
    }

    fun fetchDetail(id: Int){
        view.detailLoading(true)
        GlobalScope.launch {
            val response = api.moduleById( id )
            if(response.isSuccessful){
                withContext(Dispatchers.Main){
                    view.detailResponse(response.body()!!)
                    view.detailLoading(false)
                }
            }
            else{
                view.detailError("Terjadi Kesalahan")
            }
        }
    }

}

interface DetailView{
    fun setupListener()
    fun detailLoading(loading: Boolean)
    fun detailResponse(response: DetailResponse)
    fun detailError(messege: String)

}