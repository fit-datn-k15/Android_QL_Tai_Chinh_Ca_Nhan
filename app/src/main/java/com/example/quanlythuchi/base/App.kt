package com.example.quanlythuchi.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class App : Application() {
}
const val TAG = "TAG"