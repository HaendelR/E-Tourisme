package first.app.e_tourisme.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import first.app.e_tourisme.R;

public class Notifications {

    // Créez une méthode pour créer un canal de notification
    public static void createNotificationChannel(Context context, String channel_id, String canalName, String descriptionCanal) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = canalName;
            String description = descriptionCanal;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(description);

            // Retrieve the notification service and create the channel
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Notification display method
    public static void showNotification(Context context, String channel_id, String title, String content, Class intentClass) {
        // Create an intent for the action when the user clicks on the notification
        Intent intent = new Intent(context, intentClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);


        // Create  notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id).setSmallIcon(R.drawable.ic_notification).setContentTitle(title).setContentText(content).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent).setAutoCancel(true); //Delete the notification when the user has clicked on it

        // Show  notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
