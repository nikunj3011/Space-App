package space.di

import android.content.Context
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import space.MainViewModel
import space.util.AD_ID
import space.util.PREFERENCE_NAME
import space.util.REFRESH_TIMEOUT
import space.util.RateLimiter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.HomeViewModel
import space.api.SpaceService
import space.repository.HomeRepository
import space.ui.planet.PlanetViewModel
import space.ui.characterdetail.CharacterDetailsViewModel
import space.ui.bangboos.BangbooViewModel
import space.ui.homedata.HomeDataViewModel
import space.ui.wengines.WEngineViewModel
import space.util.Space_URL
import java.util.concurrent.TimeUnit

val appModule = module {

    factory {
        AdRequest.Builder().build()
    }

    single {
        AdLoader.Builder(androidContext(), AD_ID)
    }

    single {
        Retrofit.Builder()
            .baseUrl(Space_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpaceService::class.java)
    }

//    single {
//        val db = get<NewsDatabase>()
//        db.videosDao()
//    }

    single {
        androidContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    single {
        RateLimiter<String>(REFRESH_TIMEOUT, TimeUnit.MINUTES)
    }

    single {
        HomeRepository(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        BangbooViewModel(get())
    }

    viewModel {
        CharacterDetailsViewModel(get())
    }

    viewModel {
        HomeDataViewModel(get())
    }

    viewModel {
        PlanetViewModel(get())
    }

    viewModel {
        WEngineViewModel(get())
    }

    viewModel {
        MainViewModel()
    }

}