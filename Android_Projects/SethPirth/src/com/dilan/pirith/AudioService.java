package com.dilan.pirith;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AudioService extends Service implements
		MediaPlayer.OnPreparedListener {

	private MediaPlayer mediaPlayer = null;
	private int NOTIFICATION = R.string.service_number;
	private NotificationManager notificationManager;

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String flag = intent.getExtras().getString("FLAG");

		if (flag.endsWith("PLAY")) {
			int resId = intent.getExtras().getInt("RESOURCE_ID");
			if (null != mediaPlayer) {
				mediaPlayer.stop();
			}
			mediaPlayer = MediaPlayer.create(this, resId);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.start();
			showNotification();

		} else if (flag.endsWith("STOP")) {

			mediaPlayer.stop();
			notificationManager.cancel(NOTIFICATION);

		} else if (flag.equals("PAUSE")) {

			mediaPlayer.pause();
			notificationManager.cancel(NOTIFICATION);

		} else if (flag.equals("RESUME")) {

			mediaPlayer.start();

		}

		return START_NOT_STICKY;
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {

		mediaPlayer.start();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
		mediaPlayer = null;
		notificationManager.cancel(NOTIFICATION);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void showNotification() {
		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.service_ticker);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(
				R.drawable.bullet_icon_small, text, System.currentTimeMillis());

		Intent notificationIntent = new Intent(this, MainActivity.class);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, getText(R.string.service_name),
				text, contentIntent);

		// Send the notification.
		notificationManager.notify(NOTIFICATION, notification);
	}

}
