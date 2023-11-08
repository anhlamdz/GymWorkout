package io.strongapp.gymworkout.data.api

import io.strongapp.gymworkout.BuildConfig
import io.strongapp.gymworkout.data.api.service.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
	private const val BASE_URL = "https://gym-workout.onrender.com"

	private fun buildOkHttpClient(): OkHttpClient {
		return OkHttpClient.Builder()
			.connectTimeout(6, TimeUnit.SECONDS)
			.readTimeout(6, TimeUnit.SECONDS)
			.writeTimeout(6, TimeUnit.SECONDS)
			.addNetworkInterceptor(HttpLoggingInterceptor().apply {
				level = if (BuildConfig.DEBUG) {
					HttpLoggingInterceptor.Level.BODY
				} else {
					HttpLoggingInterceptor.Level.NONE
				}
			})
			.build()
	}

	private val retrofitProvider by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(buildOkHttpClient())
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	val apiService: APIService by lazy {
		APIService.createService(retrofitProvider)
	}

}