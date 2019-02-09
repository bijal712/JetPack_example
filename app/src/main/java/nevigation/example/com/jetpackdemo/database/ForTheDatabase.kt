package nevigation.example.com.jetpackdemo.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.content.Context
import com.facebook.stetho.dumpapp.GlobalOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

//table
@Entity
data class Person(@PrimaryKey(autoGenerate = true) var id: Long?,
                  @ColumnInfo(name = "name") var name: String?,
                  @ColumnInfo(name = "age") var age: Int?) {
    constructor():this(null,"name",0)
}

//query
@Dao
interface PersonDataDao {
    @Query("SELECT * FROM person")
    fun getAll(): LiveData<List<Person>>

    @Insert(onConflict = REPLACE)
    fun insertData(person: Person)
}

//connect table into one database
//class must be abstract
@Database(entities = [(Person::class)], version = 1)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDataDao(): PersonDataDao

    companion object {
        private var INSTANCE: PersonDatabase? = null
        fun getInstance(c: Context): PersonDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(c.applicationContext, PersonDatabase::class.java, "person.db").build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

class TodoRepo(c:Context){
    private var personDataDao : PersonDataDao?=null
    private var allTodo: LiveData<List<Person>>?=null

    init {
        val db = PersonDatabase.getInstance(c)
        personDataDao = db?.personDataDao()
        allTodo = personDataDao?.getAll()
    }

    fun getAllTodo():LiveData<List<Person>>?{
        return allTodo
    }

    fun insert(person: Person){
        GlobalScope.async {
            personDataDao?.insertData(person)
        }
    }
}

