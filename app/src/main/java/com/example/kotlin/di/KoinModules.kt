package com.example.kotlin.di

import com.example.kotlin.model.data.DataProvider
import com.example.kotlin.model.data.FireStoreProvider
import com.example.kotlin.model.repository.Repository
import com.example.kotlin.view.fragment.editor.EditorViewModel
import com.example.kotlin.view.fragment.notes.NotesViewModel
import com.example.kotlin.view.fragment.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind DataProvider::class
    single { Repository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { NotesViewModel(get()) }
}

val noteModule = module {
    viewModel { EditorViewModel(get()) }
}
