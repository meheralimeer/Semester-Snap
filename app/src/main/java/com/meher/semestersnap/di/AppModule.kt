package com.meher.semestersnap.di

import android.content.Context
import androidx.room.Room
import com.meher.semestersnap.data.AppDatabase
import com.meher.semestersnap.ui.viewmodel.GPAViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "gpa_database"
        ).build()
    }

    single { get<AppDatabase>().courseDao() }
    single { get<AppDatabase>().assignmentDao() }
    single { get<AppDatabase>().quizDao() }

    viewModel { GPAViewModel(get(), get(), get()) }
}