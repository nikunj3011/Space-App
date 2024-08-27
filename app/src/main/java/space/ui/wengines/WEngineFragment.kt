package space.ui.wengines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.databinding.FragmentWengineBinding
import space.models.api.wengines.WEngineResponseItem
import space.ui.characterdetail.WengineBottomSheetFragment
import space.ui.characterinfo.InfoBottomSheetFragment
import space.ui.common.WEngineListAdapter
import space.util.autoCleared
import java.util.Locale


class WEngineFragment : Fragment() {

    private lateinit var binding: FragmentWengineBinding
    private val viewModel by viewModel<WEngineViewModel>()
    private var weaponsAdapter by autoCleared<WEngineListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = FragmentWengineBinding.inflate(
            inflater,
            container,
            false
        )
        binding = dataBinding

        binding.shimmerFrameLayoutWeapon.startShimmer()
        binding.weaponRecyclerView.setVisibility(View.GONE)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var response = viewModel.weaponsLiveData
        binding.weaponRecyclerView.layoutManager = GridLayoutManager(context, 2)

        binding.imageViewWEnginesAbilityInfo.setOnClickListener {
            val bottomSheetFragment  = InfoBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString("info", getData())
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.setCancelable(true)
            bottomSheetFragment.show(requireActivity().supportFragmentManager, InfoBottomSheetFragment::class.java.name)
        }
        binding.searchWeaponView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        viewModel.viewModelScope.launch {
            initRecyclerView()
            delay(500)
            binding.shimmerFrameLayoutWeapon.setVisibility(View.GONE)
            binding.weaponRecyclerView.setVisibility(View.VISIBLE)
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            viewModel.weaponsLiveData.observe(viewLifecycleOwner) { result ->

                val filteredList = ArrayList<WEngineResponseItem>()
                for (i in result) {
                    if (i.name.lowercase(Locale.ROOT).contains(query)) {
                        filteredList.add(i)
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
                } else {
                    weaponsAdapter.setFilteredList(filteredList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.weaponsLiveData.observe(viewLifecycleOwner) { result ->
            this.weaponsAdapter = WEngineListAdapter(
                requireContext(),
                result
            ) { selectedItem: WEngineResponseItem ->
                listItemClicked(selectedItem)
            }
            binding.weaponRecyclerView.apply {
                adapter = weaponsAdapter
                itemAnimator?.changeDuration = 0
            }
        }
    }

    private fun listItemClicked(wengine: WEngineResponseItem){
        val bottomSheetFragment  = WengineBottomSheetFragment()

        val bundle = Bundle()
        var gson = Gson()
        var wengine = gson.toJson(wengine)

        bundle.putString("wengine", wengine.toString())
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.setCancelable(true)
        bottomSheetFragment.show(requireActivity().supportFragmentManager, WengineBottomSheetFragment::class.java.name)
    }

    private fun getData(): String{
        return """
        <h3>W-Engines are the Zenless Zone Zero equivalent of Weapons from Genshin Impact or Light Cones from Honkai: Star Rail. Here&#x27;s some more information about the system:</h3>
    <ul>
        <li>W-Engines come in 3 rarities:
            <!-- -->
            <!-- --><strong class="s-rank">S-rank</strong>,
            <!-- -->
            <!-- --><strong class="a-rank">A-rank</strong> and
            <!-- -->
            <!-- --><strong class="b-rank">B-rank</strong>.
            <!-- -->
        </li>
        <li>W-Engines increase the Agent&#x27;s Attack and another stat that changes based on the W-Engine,</li>
        <li>W-Engines also come with a passive effect that activates in the combat,</li>
        <ul>
            <li>W-Engines are restricted to the character&#x27;s Specialty - so for example,
                <!-- -->
                <!-- --><strong>only Attack characters can activate the special passive of an Attack W-Engine</strong>.
                <!-- -->
            </li>
        </ul>
        <li>You can level W-Engines in the same way as you level Agents,</li>
        <li>You can obtain W-Engines from the Signal Search, buy them in the Gadget Store or obtain them as random drops from some game modes.</li>
    </ul>
        """
    }
}