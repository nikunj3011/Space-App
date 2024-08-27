package space

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        initWindow()
        initNavController()
    }

    private fun initWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            // Prevent the app bar from overlapping the status bar
            binding.appbar.updatePadding(
                top = insets.top,
            )
            // Prevent the view from overlapping the navigation bar in landscape mode
            binding.root.updatePadding(
                right = insets.right,
            )
            windowInsets
        }
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        this.navController = navHostFragment.navController
        binding.bottomNav?.setupWithNavController(navController)
        binding.navView?.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val id = destination.id
            val showNav = id != R.id.newsDetailFragment && id != R.id.browserFragment
            binding.appbar.isVisible = showNav
            binding.bottomNav?.isVisible = showNav
            binding.navView?.isVisible = showNav
        }
    }
}