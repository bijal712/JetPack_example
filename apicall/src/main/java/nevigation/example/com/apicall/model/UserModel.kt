package nevigation.example.com.apicall.model

import android.arch.persistence.room.Entity

@Entity
class UserModel(userId: Long, title: String) {
    val id: Long? = null
    val userId: Long? = null
    var title: String? = null

    private var lastContactId = 0

    fun createContactsList(numContacts: Int): ArrayList<UserModel> {
        val contacts = ArrayList<UserModel>()

        for (i in 1..numContacts) {
            contacts.add(UserModel(userId!!, title!!))
        }

        return contacts
    }

}


