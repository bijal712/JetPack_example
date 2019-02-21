package nevigation.example.com.pagingdemo.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import nevigation.example.com.pagingdemo.paging.Cheese
import nevigation.example.com.pagingdemo.paging.CheeseDb
import nevigation.example.com.pagingdemo.paging.ioThread

class MainViewModel(app : Application) : AndroidViewModel(app) {

    var dao = CheeseDb.get(app)?.cheeseDao()
    private lateinit var allCheese: LiveData<PagedList<Cheese>>
    fun initialize(c: Context): LiveData<PagedList<Cheese>> {

        val limit = PagedList.Config.Builder()
                .setPageSize(75)
                .setEnablePlaceholders(true)
                .build();

        allCheese = LivePagedListBuilder(
                dao?.allCheesesByName()!!,
                limit)
                .build()

        return allCheese

    }

    fun insert(text: CharSequence)= ioThread {
        //GlobalScope.async {
            dao?.insert(Cheese(id = 0, name = text.toString()))
       // }
    }

    fun remove(it: Cheese)= ioThread {
        dao?.delete(it)
    }
}


