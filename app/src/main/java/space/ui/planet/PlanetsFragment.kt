package space.ui.planet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.databinding.FragmentPlanetBinding
import space.models.api.character.PlanetResponseItem
import space.ui.characterdetail.CharacterDetailBottomSheetFragment
import space.ui.common.PlanetListAdapter
import space.util.autoCleared


class PlanetsFragment : Fragment() {

    private lateinit var binding: FragmentPlanetBinding
    private val viewModel by viewModel<PlanetViewModel>()
    private var characterAdapter by autoCleared<PlanetListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = FragmentPlanetBinding.inflate(
            inflater,
            container,
            false
        )
        binding = dataBinding
        binding.shimmerFrameLayoutCharacter.startShimmer()
        binding.characterRecyclerView.setVisibility(View.GONE)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var response = viewModel.charactersLiveData
        binding.characterRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.characterRecyclerView.isNestedScrollingEnabled = false

        val linearLayoutManager: LinearLayoutManager = binding.characterRecyclerView.layoutManager as LinearLayoutManager
        binding.characterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int {
                        return SNAP_TO_START
                    }
                }
                if (dy > 0) { //check for scroll down
                    var visibleItemCount = linearLayoutManager?.findLastVisibleItemPosition()
                    smoothScroller.targetPosition = visibleItemCount!!;
                    linearLayoutManager?.startSmoothScroll(smoothScroller)

                    var cc = 0
                }
                else if (dy < 0) { //check for scroll down
                    var visibleItemCount = linearLayoutManager?.findFirstVisibleItemPosition()
                    smoothScroller.targetPosition = visibleItemCount!!;
                    linearLayoutManager?.startSmoothScroll(smoothScroller)

                    var cc = 0
                }

            }
        })
//        binding.characterRecyclerView.foc = true
        viewModel.viewModelScope.launch {
            initRecyclerView()
            delay(500)
            binding.shimmerFrameLayoutCharacter.setVisibility(View.GONE)
            binding.characterRecyclerView.setVisibility(View.VISIBLE)
        }
    }

    private fun initRecyclerView() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { result ->
            this.characterAdapter = PlanetListAdapter(requireContext(),
                result
            ) { selectedItem: PlanetResponseItem ->
                listItemClicked(selectedItem)
            }
            binding.characterRecyclerView.apply {
                adapter = characterAdapter
                itemAnimator?.changeDuration = 0
            }
        }
    }

    private fun listItemClicked(character: PlanetResponseItem){
        val bottomSheetFragment  = CharacterDetailBottomSheetFragment()

//        binding.characterRecyclerView.layoutManager?.scrollToPosition(binding.characterRecyclerView.layoutManager?.firs);
        val bundle = Bundle()
        var gson = Gson()
        var characterJson = gson.toJson(character)

        bundle.putString("characterId", characterJson)
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.setCancelable(true)
        bottomSheetFragment.show(requireActivity().supportFragmentManager, CharacterDetailBottomSheetFragment::class.java.name)
    }
}