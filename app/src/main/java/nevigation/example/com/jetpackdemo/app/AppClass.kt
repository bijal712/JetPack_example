package nevigation.example.com.jetpackdemo.app

import android.app.Application
import com.facebook.stetho.Stetho

class AppClass : Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
