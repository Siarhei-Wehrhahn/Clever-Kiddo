import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class PermissionResultReceiver(private val callback: (Boolean) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val requestCode = intent?.getIntExtra("requestCode", 0) ?: 0
        val grantResults = intent?.getIntArrayExtra("grantResults") ?: intArrayOf()

        // Hier kannst du die Berechtigungsergebnisse verarbeiten
        if (requestCode == 9000) {
            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            callback(granted)
        }
    }
}
