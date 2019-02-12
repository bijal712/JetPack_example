package nevigation.example.com.pagingdemo.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.provider.ContactsContract
import nevigation.example.com.pagingdemo.paging.Cheese
import nevigation.example.com.pagingdemo.paging.CheeseDb

class MainViewModel(app: Application) : AndroidViewModel(app) {

    var cheeseData: LiveData<PagedList<Cheese>>? = null
    private val factory = CheeseDb.get(app).cheeseDao().allCheesesByName()
    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()
        cheeseData = LivePagedListBuilder<Int, Cheese>(factory, pagedListConfig).build()
    }


    val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()


    val dao = CheeseDb.get(app).cheeseDao()

    val liveData = initializedPagedListBuilder(config).build()

}

private fun initializedPagedListBuilder(config: PagedList.Config):
        LivePagedListBuilder<String, Cheese> {

    val dataSourceFactory = object : DataSource.Factory<String, Cheese>() {
        override fun create(): DataSource<String, Cheese> {
            return CheeseDataSource()
        }
    }
    return LivePagedListBuilder<String, Cheese>(dataSourceFactory, config)
}

class CheeseDataSource : PageKeyedDataSource<String, Cheese>() {

    override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<String, Cheese>) {

        TODO("not implemented")
    }

    override fun loadAfter(
            params: LoadParams<String>,
            callback: LoadCallback<String, Cheese>) {

        TODO("not implemented")
    }

    override fun loadBefore(
            params: LoadParams<String>,
            callback: LoadCallback<String, Cheese>) {

        TODO("not implemented")
    }
}
