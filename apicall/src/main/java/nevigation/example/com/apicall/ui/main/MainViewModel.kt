package nevigation.example.com.apicall.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData
import nevigation.example.com.apicall.UserModel
import android.arch.lifecycle.LiveData
import android.util.Log
import nevigation.example.com.apicall.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class MainViewModel : ViewModel() {
    //this is the data that we will fetch asynchronously
    private lateinit var usersList: MutableLiveData<List<UserModel>>
    private lateinit var apiClient: ApiClient
    private var mainModelList: List<UserModel>? = ArrayList()


    fun getPhotographer() : LiveData<List<UserModel>>{
        //if the list is null
        usersList = MutableLiveData<List<UserModel>>()
        loadUsersData()

        //finally we will return the list
        return usersList
    }

    private fun loadUsersData() {
        apiClient = ApiClient()
        val responseCallback = apiClient.getRetrofit().getUserData()
        responseCallback.enqueue(object : Callback<List<UserModel>>{
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Log.d("TAG","fail"+t.message)
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                //finally we are setting the list to our MutableLiveData
                usersList.value = response.body();

            }
        })


    }


}
