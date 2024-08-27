package space

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.binding.HomeViewPagerAdapter
import space.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var charactersData:String = ""

    private val InterAd = "ca-app-pub-8299128249632072/4935781534"
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(), InterAd, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
//                Log.d(TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(ContentValues.TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        sf = requireActivity().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        editor = sf.edit()
        requireActivity().startService(Intent(context, viewModel::class.java))
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val homeViewPager: ViewPager2 = binding.viewPagerHome
        val homeTabLayout: TabLayout = binding.homeTabLayout
        homeViewPager.adapter = HomeViewPagerAdapter(requireActivity())
        TabLayoutMediator(homeTabLayout, homeViewPager) { tab, position ->
            when(position){
                0 -> tab.text = "Home"
                1 -> tab.text = "Solar system"
                2 -> tab.text = "Galaxy"
                3 -> tab.text = "Black holes"
                3 -> tab.text = "Universe"
            }
        }.attach()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.characterDetailsLiveData.observe(viewLifecycleOwner) { result ->
            editor.apply{

                var gson = Gson()
                var characterJson = gson.toJson(result)
                putString("charactersDataZZZ", characterJson)
//                putString("charactersDataZZZModifiedDate", Calendar.getInstance().time.toString())
                commit()
            }
        }
    }

    override fun onDestroy() {
//        showAd()
        super.onDestroy()
    }

    private fun showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}