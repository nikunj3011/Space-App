package space.ui.characterdetail

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.core.text.color
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import space.animations.Fade
import space.animations.animationXFade
import space.databinding.FragmentCharacterDetailBottomSheetBinding
import space.models.api.character.PlanetResponseItem
import space.models.api.characterdetail.ListCharacterDetailResponse


class CharacterDetailBottomSheetFragment :  BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCharacterDetailBottomSheetBinding
    private val viewModel by viewModel<CharacterDetailsViewModel>()
    private lateinit var characterId : String
    private lateinit var characterModel : PlanetResponseItem
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var charactersModel : ListCharacterDetailResponse

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = FragmentCharacterDetailBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        characterId = arguments?.getString("characterId").toString()
        if(characterId == null)
        {
            characterId = "1"
        }
        var gson = Gson()
        characterModel = gson.fromJson(characterId, PlanetResponseItem::class.java)
        binding = dataBinding

        view?.parent
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sf = requireActivity().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        editor = sf.edit()
        var charactersData = sf.getString("charactersDataZZZ","")
        if (!charactersData.isNullOrEmpty())
        {
            var gson = Gson()
            charactersModel = gson.fromJson(charactersData, ListCharacterDetailResponse::class.java)
            var character = charactersModel.findLast { c -> c.slug == characterModel.name }

            binding.txtCharacterNameBottomSheet.text = character?.fullName
            binding.textCharacterNameTransparentBottomSheet.text = characterModel?.name
            binding.textDescriptionBottomSheet.text = ""

//            binding.txtCharacterStyleBottomSheet.text = character?.style
            Glide.with(view)
                .load("https://www.prydwen.gg${character?.cardImage?.localFile?.childImageSharp?.gatsbyImageData?.images?.fallback?.src}")
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.imageViewCharacterBottomSheet)
            binding.textCharFactionSmall.text = character?.faction

            binding.textViewBestWEngineAgent.text = character?.build?.engines?.first()?.weapon;


            binding.textRatingsCharacterBottomSheet.text = "Shiyu Defence: " +character?.ratings?.shiyu?.toString()
            binding.textMainCategoryCharacterBottomSheet.text = "Main Role: " +character?.tierListCategory

            var characterTalents = SpannableStringBuilder()
            if(!character?.talents.isNullOrEmpty()){
                for (attribute in character?.talents!!){
                    characterTalents = characterTalents.append()
                        .bold{color(Color.WHITE, { append(attribute?.name) })}
                        .append(" : ")
                        .append(HtmlCompat.fromHtml(attribute.desc, HtmlCompat.FROM_HTML_MODE_COMPACT))
                        .append("\n\n")
                }
            }
            binding.textViewCharacterTalents.text = characterTalents

            var otherRoles = "Other Role: "
            if(!character?.tierListTags.isNullOrEmpty()){
                for(tiertag in character?.tierListTags!!){
                    otherRoles = "$otherRoles $tiertag"
                }
            }
            binding.textSubCategoryCharacterBottomSheet.text = otherRoles

            var stats1 = ""
            if(!character?.build?.main_4.isNullOrEmpty()){
                for(stat in character?.build?.main_4!!){
                    stats1 = "$stats1 ${stat.stat}"
                }
            }
            binding.textViewStat1.text = stats1

            var stats2 = ""
            if(!character?.build?.main_4.isNullOrEmpty()){
                for(stat in character?.build?.main_5!!){
                    stats2 = "$stats2 ${stat.stat}"
                }
            }
            binding.textViewStat2.text = stats2

            var stats3 = ""
            if(!character?.build?.main_4.isNullOrEmpty()){
                for(stat in character?.build?.main_6!!){
                    stats3 = "$stats3 ${stat.stat}"
                }
            }
            binding.textViewStat3.text = stats3

                binding.txtCharacterNameBottomSheet.animationXFade(Fade.FADE_IN_RIGHT)
                binding.textCharacterNameTransparentBottomSheet.animationXFade(Fade.FADE_IN_RIGHT)
                binding.imageViewCharacterRegionBottomSheet.animationXFade(Fade.FADE_IN_UP)
                binding.imageViewCharacterBottomSheet.animationXFade(Fade.FADE_IN_UP)
//            binding.textCharDesSmall.text = characterModel?.bio.toString()

            }
        }
    }
