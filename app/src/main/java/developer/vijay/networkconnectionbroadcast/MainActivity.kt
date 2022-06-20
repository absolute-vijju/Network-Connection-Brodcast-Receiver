package developer.vijay.networkconnectionbroadcast

import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import developer.vijay.networkconnectionbroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        snackBar = if (!isConnected) {
            Snackbar.make(mBinding.root, "You are offline", Snackbar.LENGTH_INDEFINITE).apply {
                setTextColor(Color.WHITE)
                setBackgroundTint(Color.RED)
                setAction("Turn on") {

                }
                show()
            }
        } else {
            Snackbar.make(mBinding.root, "Back to online.", Snackbar.LENGTH_INDEFINITE).apply {
                setTextColor(Color.WHITE)
                setBackgroundTint(Color.GREEN)
                show()
            }
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        ConnectivityReceiver.connectivityReceiverListener = null
    }
}