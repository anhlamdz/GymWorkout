package io.strongapp.gymworkout.view.FilterExerciseDialog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ShutDownReceiver : BroadcastReceiver() {
	override fun onReceive(p0: Context?, p1: Intent?) {
		if (Intent.ACTION_SHUTDOWN == p1?.action){
			clearSharedPreferences(p0)
		}

	}
	private fun clearSharedPreferences(context: Context?) {
		val preferences =
			context?.getSharedPreferences("filter_preferences", Context.MODE_PRIVATE)
		val editor = preferences?.edit()
		editor?.clear()
		editor?.apply()
	}
}