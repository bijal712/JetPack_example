package nevigation.example.com.apicall

import android.arch.persistence.room.*
import retrofit2.Call
import retrofit2.http.GET

@Entity
class UserModel {
        val userId: Long? = null
        val id: Long? = null
        var title: String? = null

        override fun toString(): String {
            return "AndroidBean(userId=$userId, id=$id, title=$title)"

    }
}


