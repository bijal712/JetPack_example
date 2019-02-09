package nevigation.example.com.jetpackdemo.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import nevigation.example.com.jetpackdemo.database.Person
import nevigation.example.com.jetpackdemo.database.TodoRepo

class MainViewModel : ViewModel() {
    private var todoRepo: TodoRepo? = null
    private var allTodo: LiveData<List<Person>>? = null

    fun initialize(c: Context) {
        todoRepo = TodoRepo(c)
        allTodo = todoRepo?.getAllTodo()
    }

    fun gteAllTodo(): LiveData<List<Person>>? {
        return todoRepo?.getAllTodo()
    }

    fun insert(person: Person){
        todoRepo?.insert(person)
    }
}
