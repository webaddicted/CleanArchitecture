package com.webaddicted.techcleanarch.global.koin


import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.webaddicted.database.database.AppDatabase
import com.webaddicted.database.utils.DbConstant
import com.webaddicted.network.apiutils.ApiConstant
import com.webaddicted.network.apiutils.ApiServices
import com.webaddicted.network.apiutils.ReflectionUtil
import com.webaddicted.techcleanarch.AppApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
val appModule = module {

    /* PROVIDE GSON SINGLETON */
    single<Gson> {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.setLenient().create()
    }

    /* PROVIDE RETROFIT SINGLETON */
    single {
        var loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        var httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        httpClient.connectTimeout(ApiConstant.API_TIME_OUT, TimeUnit.MILLISECONDS)
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder().build()
            chain.proceed(request)
        }
        var okHttpClient = httpClient.build()

        Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get() as Gson))
            .build()
    }

    /*Provide API Service Singleton */
    single {
        (get<Retrofit>()).create<ApiServices>(ApiServices::class.java)
    }
    single {
        Room.databaseBuilder(
            (androidApplication() as AppApplication),
            AppDatabase::class.java,
            DbConstant.DB_NAME
        ).allowMainThreadQueries().build()
        //.addMigrations(migration4To5, migration5To6).build()
    }
//    single { (get() as AppDatabase).userInfoDao() }
    single { ReflectionUtil(get()) }
}