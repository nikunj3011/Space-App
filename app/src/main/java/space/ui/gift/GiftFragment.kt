package space.ui.gift

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import space.databinding.FragmentGiftBinding

class GiftFragment : Fragment() {

    private val InterAd = "ca-app-pub-8299128249632072/4935781534"
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var binding: FragmentGiftBinding
    //    private val viewModel by viewModel<PlanetViewModel>()
//    private var characterAdapter by autoCleared<PlanetListAdapter>()
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
        val dataBinding = FragmentGiftBinding.inflate(
            inflater,
            container,
            false
        )
        binding = dataBinding
        binding.webViewGift.webViewClient = WebViewClient()
        binding.webViewGift.settings.javaScriptEnabled = true
//        binding.webViewGift.loadUrl("https://wuthering.gg/map")
        binding.webViewGift.loadUrl("https://game8.co/games/Zenless-Zone-Zero/archives/435683")
        return binding.root
    }


    override fun onDestroy() {
        showAd()
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