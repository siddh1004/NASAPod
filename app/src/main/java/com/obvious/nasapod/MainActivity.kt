package com.obvious.nasapod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        setContentView(R.layout.activity_main)

        adapter = RecyclerAdapter(nasaPhotoArrayList)
        recyclerView.adapter = adapter
        setRecyclerViewScrollListener()

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        myCompositeDisposable = CompositeDisposable()
        loadData()
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
                .subscribe(this::handleResponse))

            calendar.add(Calendar.DAY_OF_YEAR, -1)
            date = dateFormat.format(calendar.time)
            i--
        }

    }

    private fun handleResponse(nasaPhoto: NasaPhoto) {
        nasaPhotoArrayList.add(nasaPhoto)
        adapter.notifyItemInserted(nasaPhotoArrayList.size-1)

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }

}
