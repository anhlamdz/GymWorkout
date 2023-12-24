package io.strongapp.gymworkout.view


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import io.strongapp.gymworkout.R


class NotificationFinish {

	companion object {
		private const val CHANNEL_ID = "my_channel_id"
		private const val CHANNEL_NAME = "My Channel"
		private const val CHANNEL_DESCRIPTION = "Description for my channel"
	}

	fun showNotification(context: Context, title: String, content: String) {
		val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel = NotificationChannel(
				CHANNEL_ID,
				CHANNEL_NAME,
				NotificationManager.IMPORTANCE_DEFAULT
			)
			channel.description = CHANNEL_DESCRIPTION
			channel.enableLights(true)
			channel.lightColor = Color.RED
			channel.enableVibration(true)
			notificationManager.createNotificationChannel(channel)
		}

		Log.d("Notification", "Creating notification")
		val builder = NotificationCompat.Builder(context, CHANNEL_ID)
			.setSmallIcon(R.drawable.ic_img_checked)
			.setContentTitle(title)
			.setContentText(content)
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)

		val notification: Notification = builder.build()
		notificationManager.notify(1, notification)
		Log.d("Notification", "Notification created and sent")

	}
}