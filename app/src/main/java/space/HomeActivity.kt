package space

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import space.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    private lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate((layoutInflater))
        enableEdgeToEdge()
        initNavController()

        MobileAds.initialize(
            this
        ) { }
//        setContentView(R.layout.sticker_packs_list_item);
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        setContentView(binding.root)
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        this.navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val id = destination.id
            val showNav = id != R.id.characterFragment && id != R.id.characterFragment
//            binding.bottomNav?.isVisible = showNav
        }
    }



//    private fun initNavController() {
//        binding.bottomNav1.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.homeFragment -> replaceFragment(HomeFragment())
//                R.id.characterFragment -> replaceFragment(PlanetsFragment())
//                else -> {
//
//                }
//            }
//            true
//        }
//    }
//
//    private fun replaceFragment(fragment : Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
//        fragmentTransaction.commit()
//    }
}