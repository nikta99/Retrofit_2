package com.example.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyAdapter.Listener {

    private var myAdapter: MyAdapter? = null
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myLoginUserArrayList: ArrayList<LoginUser>? = null
    private val BASE_URL = "https://api.github.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        loadData()

    }

    private fun initRecyclerView() {


        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        loginUser_list.layoutManager = layoutManager

    }

    private fun loadData() {


        val requestInterface = Retrofit.Builder()


            .baseUrl(BASE_URL)

//Specify the converter factory to use for serialization and deserialization//

            .addConverterFactory(GsonConverterFactory.create())

//Add a call adapter factory to support RxJava return types//

            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

//Build the Retrofit instance//

            .build().create(UpData::class.java)

//Add all RxJava disposables to a CompositeDisposable//

        myCompositeDisposable?.add(requestInterface.UpData()


            .observeOn(AndroidSchedulers.mainThread())

            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse))

    }

    private fun handleResponse(loginUser:List<LoginUser>) {

        myLoginUserArrayList = ArrayList(loginUser)
        myAdapter = MyAdapter(myLoginUserArrayList!!, this)


        loginUser_list.adapter = myAdapter

    }

    override fun onItemClick(loginUser: LoginUser) {

//If the user clicks on an item, then display a Toast//

        Toast.makeText(this, "You clicked: ${loginUser.login}", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()

//Clear all your disposables//

        myCompositeDisposable?.clear()

    }

}