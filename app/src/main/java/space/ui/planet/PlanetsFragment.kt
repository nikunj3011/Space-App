package space.ui.planet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.databinding.FragmentPlanetBinding
import space.models.api.character.AgentResponseItem
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var response = viewModel.charactersLiveData
        binding.characterRecyclerView.layoutManager = GridLayoutManager(context, 1)
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
            ) { selectedItem: AgentResponseItem ->
                listItemClicked(selectedItem)
            }
            binding.characterRecyclerView.apply {
                adapter = characterAdapter
                itemAnimator?.changeDuration = 0
            }
        }
    }

    private fun listItemClicked(character: AgentResponseItem){
        val bottomSheetFragment  = CharacterDetailBottomSheetFragment()

        val bundle = Bundle()
        var gson = Gson()
        var characterJson = gson.toJson(character)

        bundle.putString("characterId", characterJson)
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.setCancelable(true)
        bottomSheetFragment.show(requireActivity().supportFragmentManager, CharacterDetailBottomSheetFragment::class.java.name)
    }
}