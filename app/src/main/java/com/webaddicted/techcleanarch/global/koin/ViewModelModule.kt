package com.webaddicted.techcleanarch.global.koin

import com.webaddicted.techcleanarch.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}