package nevigation.example.com.apicall.utils


import nevigation.example.com.apicall.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("todos")
    fun getUserData() : Call<List<UserModel>>
}