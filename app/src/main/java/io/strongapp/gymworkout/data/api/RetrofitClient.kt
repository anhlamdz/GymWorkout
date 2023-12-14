package io.strongapp.gymworkout.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import io.strongapp.gymworkout.BuildConfig
import io.strongapp.gymworkout.base.App
import io.strongapp.gymworkout.data.api.service.APIService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
	private const val BASE_URL = "https://gym-workout.onrender.com"


	private fun buildOkHttpClient(context: Context): OkHttpClient {
		val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
		val cache = Cache(context.cacheDir, cacheSize)

		val cacheControlInterceptor = Interceptor { chain ->
			var request = chain.request()
			request = if (hasNetwork(context)!!)
				request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
			else
				request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
			chain.proceed(request)
		}

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
			.addInterceptor(cacheControlInterceptor)
			.cache(cache) // Thêm cache vào OkHttpClient
			.build()
	}
	fun hasNetwork(context: Context): Boolean? {
		var isConnected: Boolean? = false // Initial Value
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
		if (activeNetwork != null && activeNetwork.isConnected)
			isConnected = true
		return isConnected
	}




	private val retrofitProvider by lazy {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(buildOkHttpClient(App.applicationContext()))
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	val apiService: APIService by lazy {
		APIService.createService(retrofitProvider)
	}

}