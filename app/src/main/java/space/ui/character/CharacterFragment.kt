package space.ui.character

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
import space.databinding.FragmentCharacterBinding
import space.models.api.character.AgentResponseItem
import space.ui.characterdetail.CharacterDetailBottomSheetFragment
import space.ui.characterinfo.InfoBottomSheetFragment
import space.ui.common.CharacterListAdapter
import space.util.autoCleared
import java.util.Locale


class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private val viewModel by viewModel<CharacterViewModel>()
    private var characterAdapter by autoCleared<CharacterListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = FragmentCharacterBinding.inflate(
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
        binding.searchCharacterView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText, false)
                return true
            }

        })

        binding.imageViewIce.setOnClickListener { filterList("ice", true) }
        binding.imageViewFire.setOnClickListener {filterList("fire", true)}
        binding.imageViewElectric.setOnClickListener {filterList("electric", true)}
        binding.imageViewPhysical.setOnClickListener {filterList("physical", true)}
        binding.imageViewEther.setOnClickListener {filterList("ether", true)}
        binding.imageViewFilterOff.setOnClickListener {filterList("", false)
            binding.searchCharacterView.setQuery("", true)}
        viewModel.viewModelScope.launch {
            initRecyclerView()
            delay(500)
            binding.shimmerFrameLayoutCharacter.setVisibility(View.GONE)
            binding.characterRecyclerView.setVisibility(View.VISIBLE)
        }

        binding.imageViewCharactersAbilityInfo.setOnClickListener {
            val bottomSheetFragment  = InfoBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString("info", getData())
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.setCancelable(true)
            bottomSheetFragment.show(requireActivity().supportFragmentManager, InfoBottomSheetFragment::class.java.name)
        }
    }

    private fun filterList(query: String?, isElement: Boolean) {
        if (query != null) {
            viewModel.charactersLiveData.observe(viewLifecycleOwner) { result ->

                val filteredList = ArrayList<AgentResponseItem>()
                if(isElement){
                    for (i in result) {
                        if (i.element.lowercase(Locale.ROOT).contains(query)) {
                            filteredList.add(i)
                        }
                    }
                }
                else{
                    for (i in result) {
                        if (i.name.lowercase(Locale.ROOT).contains(query)) {
                            filteredList.add(i)
                        }
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
                } else {
                    characterAdapter.setFilteredList(filteredList)
                }
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.charactersLiveData.observe(viewLifecycleOwner) { result ->
            this.characterAdapter = CharacterListAdapter(requireContext(),
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

    private fun getData(): String{
        return """
        <h3>Here are all the combat stats agents can gain and what they do:</h3>
        <br/>
    <ul>
        <li><strong>HP</strong> - How much damage an Agent can take before falling in combat.
            <!-- -->
        </li>
        <li><strong>ATK</strong> - How much damage an Agent deals.
            <!-- -->
        </li>
        <li><strong>DEF</strong> - Reduces the damage an Agent takes.
            <!-- -->
        </li>
        <li><strong>Impact</strong> - Attacking enemies increases their Daze. A higher Impact stat allows more Daze to accumulate with each attack.
            <!-- -->
        </li>
        <li><strong>Crit Rate</strong> - How likely it is for the Agent to land a critical hit.
            <!-- -->
        </li>
        <li><strong>Crit DMG</strong> - Determines the damage multiplier when landing a critical hit.
            <!-- -->
        </li>
        <li><strong>PEN</strong> - Ignores a set amount of the target’s DEF based on the PEN stat when attacking an enemy.
            <!-- -->
        </li>
        <li><strong>Pen Ratio</strong> - Ignores a percentage of the target’s DEF based on the PEN Ratio stat when attacking an enemy.
            <!-- -->
        </li>
        <li><strong>Anomaly Mastery</strong> - Affects the efficiency of Anomaly Buildup. Each point in Anomaly Mastery is equivalent to 1% of Anomaly Buildup Rate.
            <!-- -->
        </li>
        <li><strong>Anomaly Proficiency</strong> - Enhances the damage inflicted by Attribute Anomalies. Each point in Anomaly Proficiency is equivalent to 1% of Attribute Anomaly DMG.
            <!-- -->
        </li>
        <li><strong>Energy Regen</strong> - After entering combat, Agents passively generate Energy every second. The higher this value is, the more Energy gained per second.
            <!-- -->
        </li>
        <li><strong>Energy Generation Rate</strong> - Energy Generation Rate will increase the Energy generated through Energy Regen, and attacking enemies.
            <!-- -->
        </li>
    </ul>
        <br/>
        <br/>
    
    <h3>Character Attributes</h3>
    <p>All agents are categorized into one of five
        <!-- -->
        <!-- --><strong>Attributes</strong>:
        <!-- -->
    </p>
    <ul>
        <li><strong class="Fire">Fire</strong></li>
        <li><strong class="Electric">Electric</strong></li>
        <li><strong class="Ice">Ice</strong></li>
        <li><strong class="Physical">Physical</strong></li>
        <li><strong class="Ether">Ether</strong></li>
    </ul>
    <p>Combining Agents with specific Elemental attributes and Factions in the same squad allows you to activate their additional passive skill.</p>
        
        <br/>
        <br/>
    
    <h3>Specialty</h3>
    <p>In addition Agents can specialize in five different
        <!-- -->
        <!-- --><strong>Specialties</strong> that define their role in the team:
        <!-- -->
    </p>
    <ul>
        <li><strong>Attack</strong> - Agents with the Attack specialty possess formidable damage output capabilities, excelling at rapidly racking up damage directly through attacks to defeat enemies and bring the combat to a swift end.
            <!-- -->
        </li>
        <li><strong>Stun</strong> - Agents with Stun specialty possess powerful control abilities, excelling at building up Daze to cause enemies to be Stunned, thus creating opportunities for the squad to deal damage.
            <!-- -->
        </li>
        <li><strong>Support</strong> - Agents with Support specialty are able to aid and enhance other Agents in battle, excelling at buffing friendly units to improve the combat effectiveness of the whole squad.
            <!-- -->
        </li>
        <li><strong>Defense</strong> - Agents with Defense specialty have strong survivability, excelling at tanking attacks to counterattack, seizing the upper hand for their side in the heat of battle.
            <!-- -->
        </li>
        <li><strong>Anomaly</strong> - Agents with Anomaly specialty are exceptional with applying debuffs, excelling at accumulating Anomaly Buildup to weaken enemies and deal damage by triggering Attribute Anomalies.
            <!-- -->
        </li>
    </ul>
    
    <br/>
        <br/>
    
    <p>Enemies come in different types and are vulnerable to different elemental damage:</p>
    <ul>
        <li><strong>Organic and Corrupted</strong> are vulnerable to
            <!-- -->
            <!-- --><strong class="zzz-color Fire">Fire</strong>-Type Attacks.
            <!-- -->
        </li>
        <li><strong>Machines</strong> are vulnerable to
            <!-- -->
            <!-- --><strong class="zzz-color Electric">Electric</strong>-Type Attacks.
            <!-- -->
        </li>
        <li><strong>Mutants</strong> are vulnerable to
            <!-- -->
            <!-- --><strong class="zzz-color Ice">Ice</strong>-Type Attacks.
            <!-- -->
        </li>
    </ul>
        <br/>
        <br/>
    
    <h3>Attribute Anomaly</h3>
    <p>When an agent deals attribute damage, they
        <!-- -->
        <!-- --><strong>inflict a certain amount of Anomaly Buildup</strong>. Once a certain level of buildup is reached, the enemy will be afflicted with an attribute anomaly debuff of the corresponding attribute. Additionally, exploiting enemies weakness to
        certain Attributes to trigger Attribute Anomaly will intensify the Anomaly effect. As we mentioned in the stats section above,
        <!-- -->
        <!-- --><strong>Anomaly Mastery stat will increase the buildup of the Anomaly, while Anomaly Proficiency will increase the damage dealt by it.</strong></p>
    <p>Combining 2 different attributes will override the original anomaly and trigger an additional effect
        <!-- --><strong>Disorder</strong> which deals extra damage calculated based on original anomalies damage and accumulates Daze.
        <!-- -->
    </p>
        <br/>
        <br/>
    
    <p>Here are all the Anomaly Debuffs that exist in the game:</p>
    <div class="anomaly-debuff-table">
        <div class="ano-header">
            <div>Element</div>
            <div>Activation</div>
            <div>Debuff</div>
        </div>
        <div class="single-row">
            <div><strong class="zzz-color Electric">Electric</strong></div>
            <div><span class="on-mobile">Activation</span>Dealing Electric damage to enemies accumulates the Electric Attribute Anomaly, which triggers the Shock effect when enough is accumulated.
                <!-- -->
            </div>
            <div><span class="on-mobile">Debuff</span><strong class="zzz-color Electric">Shock:</strong> Being attacked intermittently triggers additional Electric damage and interrupts enemy actions. Machine enemies are unable to move while Shocked.
                <!-- -->
            </div>
        </div>
        <div class="single-row">
            <div><strong class="zzz-color Physical">Physical</strong></div>
            <div><span class="on-mobile">Activation</span>Dealing Physical damage to enemies accumulates the Physical Attribute Anomaly, which triggers the Assault effect when enough is accumulated.
                <!-- -->
            </div>
            <div><span class="on-mobile">Debuff</span>
                <ul>
                    <li><strong class="zzz-color Physical">Assault:</strong>
                        <!-- -->Interrupts the enemy, deals massive Physical damage and increases the Daze inflicted on the target.
                        <!-- -->
                    </li>
                </ul>
            </div>
        </div>
        <div class="single-row">
            <div><strong class="zzz-color Ice">Ice</strong></div>
            <div><span class="on-mobile">Activation</span>Dealing Ice damage to enemies accumulates the Ice Attribute Anomaly, which triggers the Freeze and Frostbite effects when enough is accumulated.
                <!-- -->
            </div>
            <div><span class="on-mobile">Debuff</span>
                <ul>
                    <li><strong class="zzz-color Ice">Freeze:</strong> Prevents taking action for a certain period, and triggers Shatter at the end of the effect, dealing Ice damage.
                        <!-- -->
                    </li>
                    <li><strong class="zzz-color Ice">Frostbite:</strong>
                        <!-- -->Increases the CRIT DMG taken by the target for a period of time.
                        <!-- -->
                    </li>
                </ul>
            </div>
        </div>
        <div class="single-row">
            <div><strong class="zzz-color Fire">Fire</strong></div>
            <div><span class="on-mobile">Activation</span>Dealing Fire damage to enemies accumulates the Fire Attribute Anomaly, which triggers the Burn effect when enough is accumulated.
                <!-- -->
            </div>
            <div><span class="on-mobile">Debuff</span><strong class="zzz-color Fire">Burn:</strong> Deals continuous Fire damage. Organic enemies are unable to move while Burned.
                <!-- -->
            </div>
        </div>
        <div class="single-row">
            <div><strong class="zzz-color Ether">Ether</strong></div>
            <div><span class="on-mobile">Activation</span>Dealing Ether damage to enemies accumulates the Ether Attribute Anomaly, causing the Corruption effect when enough is accumulated.
                <!-- -->
            </div>
            <div><span class="on-mobile">Debuff</span><strong class="zzz-color Ether">Corruption:</strong> Causes additional Ether damage when attacked. Corrupted Ethereal enemies will also attack both friend and foe.
                <!-- -->
            </div>
        </div>
    </div>
    <p>To learn more about Anomaly Debuffs, check this great video:</p>
    <div class="video row">
        <div class="col-lg-6 col-12">
            <div class="video-responsive"><iframe src="https://www.youtube.com/embed/bi6IJXh_-9A" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen="" title="Embedded youtube"></iframe></div>
        </div>
    </div>
        """
    }
}