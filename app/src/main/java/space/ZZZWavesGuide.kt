package space

import android.app.Application
import space.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ZZZWavesGuide : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ZZZWavesGuide)
            modules(appModule)
        }
    }

}