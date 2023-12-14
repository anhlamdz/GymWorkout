package io.strongapp.gymworkout.base

import android.app.Application
import android.content.Context

class App : Application() {
	companion object{
		private lateinit var instance : App

		fun applicationContext() : Context{
			return instance.applicationContext
		}
	}

	override fun onCreate() {
		super.onCreate()
		instance = this
	}
}