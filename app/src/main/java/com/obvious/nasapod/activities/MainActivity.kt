package com.obvious.nasapod.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obvious.nasapod.NasaApiService
import com.obvious.nasapod.models.NasaPhoto
import com.obvious.nasapod.R
import com.obvious.nasapod.adapters.RecyclerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){

    private lateinit var adapter: RecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private val lastVisibleItemPosition: Int
        get() = gridLayoutManager.findLastVisibleItemPosition()

    private var myCompositeDisposable: CompositeDisposable? = null
    private var nasaPhotoArrayList: ArrayList<NasaPhoto> = ArrayList()

    private val apiService = NasaApiService.create()
    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var date: String = dateFormat.format(calendar.time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myCompositeDisposable = CompositeDisposable()

        setContentView(R.layout.activity_main)
        setRecyclerView()

        if(nasaPhotoArrayList.size <=0){
            loadData()
        }
    }

    private fun setRecyclerView(){
        adapter = RecyclerAdapter(nasaPhotoArrayList)
        recyclerView.adapter = adapter
        setRecyclerViewScrollListener()

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    loadData()
                }
            }
        })
    }

    private fun loadData() {

        var i = 10
        while (i > 0){

            myCompositeDisposable?.add(apiService.getData("TNhIQVtAvRJjCAAfFAPpMq6Ogo9WJoMuMKdReEQc", date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result -> handleResponse(result) },
                    { error -> displayError(error) }
                ))

            calendar.add(Calendar.DAY_OF_YEAR, -1)
            date = dateFormat.format(calendar.time)
            i--
        }

    }

    private fun handleResponse(nasaPhoto: NasaPhoto) {
        if (nasaPhoto.media_type == "image"){
            nasaPhotoArrayList.add(nasaPhoto)
            adapter.notifyItemInserted(nasaPhotoArrayList.size-1)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }

    fun displayError(error: Throwable) {
        Log.i("error", "Error while fetching data..", error)
    }
}
