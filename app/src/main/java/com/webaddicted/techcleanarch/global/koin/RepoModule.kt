package com.webaddicted.techcleanarch.global.koin
import com.webaddicted.data.repo.NewsRepository
import org.koin.dsl.module
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
val repoModule = module {

    single { NewsRepository(get()) }

}