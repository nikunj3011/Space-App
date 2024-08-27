package space.ui.bangboos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.databinding.FragmentBangbooBinding
import space.models.api.bangboo.BangBoosResponseItem
import space.ui.characterinfo.InfoBottomSheetFragment
import space.ui.common.BangbooListAdapter
import space.util.autoCleared
import java.util.Locale


class BangbooFragment : Fragment() {

    var binding by autoCleared<FragmentBangbooBinding>()
    private val viewModel by viewModel<BangbooViewModel>()
    private var echosAdapter by autoCleared<BangbooListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = FragmentBangbooBinding.inflate(
            inflater,
            container,
            false
        )
        binding = dataBinding

        binding.shimmerFrameLayoutEcho.startShimmer()
        binding.echoRecyclerView.setVisibility(View.GONE)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var response = viewModel.BangboosLiveData
        binding.echoRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        binding.imageViewBangbooAbilityInfo.setOnClickListener {
            val bottomSheetFragment  = InfoBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString("info", getData())
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.setCancelable(true)
            bottomSheetFragment.show(requireActivity().supportFragmentManager, InfoBottomSheetFragment::class.java.name)
        }

        binding.searchEchoView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            binding.shimmerFrameLayoutEcho.setVisibility(View.GONE)
            binding.echoRecyclerView.setVisibility(View.VISIBLE)
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            viewModel.BangboosLiveData.observe(viewLifecycleOwner) { result ->

                val filteredList = ArrayList<BangBoosResponseItem>()
                for (i in result) {
                    if (i.name.lowercase(Locale.ROOT).contains(query)) {
                        filteredList.add(i)
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
                } else {
                    echosAdapter.setFilteredList(filteredList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.BangboosLiveData.observe(viewLifecycleOwner) { result ->
            this.echosAdapter = BangbooListAdapter(
                requireContext(),
                result
            ) { selectedItem: BangBoosResponseItem ->
                listItemClicked(selectedItem)
            }
            binding.echoRecyclerView.apply {
                adapter = echosAdapter
                itemAnimator?.changeDuration = 0
            }
        }

    }

    private fun listItemClicked(fruit: BangBoosResponseItem){
        Toast.makeText(
            requireActivity(),
            "Supplier is : ${fruit.name}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getData(): String{
        return """
        <h5>Bangboo were originally created to assist New Eridu residents with evacuation in Hollow disasters. When a Hollow occurs in New Eridu&#x27;s residential areas, Bangboo will immediately receive disaster information and evacuation guidance from the public
        network, and help people around them avoid the hazard-prone areas.</h5>
    <p>If you ever played Honkai Impact 3, then
        <!-- -->
        <!-- --><strong>Bangboo are really similar to ELFs</strong> - they are the 4th team member who supports the currently active character with both active skills and buffs. Similar to Agents, Bangboo come in two ranks -
        <!-- --><strong class="s-rank">S-rank</strong> and
        <!-- -->
        <!-- --><strong class="a-rank">A-rank</strong>.
        <!-- -->
    </p>
    <h5>How to obtain Bangboo?</h5>
    <p>You can pull them from the Bangboo Oriented Signal Search. You can only use
        <!-- --><strong>Boopons</strong> on this banner -
        <!-- -->
        <!-- --><strong>Boopons</strong> can be obtained from missions, events and various modes in Hollow Deep Dive Zone and can&#x27;t be bought using real-life currency.
        <!-- -->
    </p>
        """
    }
}