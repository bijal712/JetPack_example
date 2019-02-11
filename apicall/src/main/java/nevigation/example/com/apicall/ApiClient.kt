package nevigation.example.com.apicall

import nevigation.example.com.apicall.utils.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {


    fun getRetrofit(): ApiInterface {
        val BASE_URL = "https://jsonplaceholder.typicode.com/"
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create<ApiInterface>(ApiInterface::class.java)
    }

}