package nevigation.example.com.apicall.paging

import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import nevigation.example.com.apicall.model.UserModel
import java.security.Key

class FeedKeySource: ItemKeyedDataSource<Key, UserModel>() {
    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<UserModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<UserModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<UserModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getKey(item: UserModel): Key {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}