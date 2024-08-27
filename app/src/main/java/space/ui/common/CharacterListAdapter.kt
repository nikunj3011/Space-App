package space.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import space.R
import space.models.api.character.AgentResponseItem


class CharacterListAdapter(
    private val contextCharacter: Context,
    private var fruitsList:List<AgentResponseItem>,
    private val clickListener:(AgentResponseItem)->Unit
) : RecyclerView.Adapter<MyViewHolder3>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder3 {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.layout_character_list,parent,false)
        return MyViewHolder3(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder3, position: Int) {
        val fruit = fruitsList[position]
        holder.bind(contextCharacter, fruit,clickListener)
    }

    fun setFilteredList(mList: List<AgentResponseItem>){
        this.fruitsList = mList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return fruitsList.size
    }

}
class MyViewHolder3(val view: View):RecyclerView.ViewHolder(view) {
    private var touchRelease: Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    fun bind(
        contextCharacter: Context,
        character: AgentResponseItem,
        clickListener: (AgentResponseItem) -> Unit
    ) {
        val characterTextView = view.findViewById<TextView>(R.id.characterName)
        characterTextView.text = character.name
        val characterRecyclerView = view.findViewById<RecyclerView>(R.id.characterRecyclerView)

        val imageViewCharacterType = view.findViewById<ImageView>(R.id.imageViewCharacterType)
        if (character?.element != null) {
            when (character.element.lowercase()) {
                "ice" -> {
                    Glide.with(view)
                        .load(R.drawable.ice)
                        .into(imageViewCharacterType)
                }

                "fire" -> {
                    Glide.with(view)
                        .load(R.drawable.fire)
                        .into(imageViewCharacterType)
                }

                "electric" -> {
                    Glide.with(view)
                        .load(R.drawable.electric)
                        .into(imageViewCharacterType)
                }

                "physical" -> {
                    Glide.with(view)
                        .load(R.drawable.physical)
                        .into(imageViewCharacterType)
                }

                "ether" -> {
                    Glide.with(view)
                        .load(R.drawable.ether)
                        .into(imageViewCharacterType)
                }

                else -> {
                    Glide.with(view)
                        .load(R.drawable.electric)
                        .into(imageViewCharacterType)
                }
            }
        }

        val imageViewCharacterFaction = view.findViewById<ImageView>(R.id.imageViewCharacterFaction)
        val imageViewCharacterAgencyShadowed = view.findViewById<ImageView>(R.id.imageViewCharacterAgencyShadowed)
        if (character?.faction != null) {
            when (character.faction) {
                "Cunning Hares" -> {
                    Glide.with(view)
                        .load(R.drawable.the_cunning_hares)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.the_cunning_hares)
                        .into(imageViewCharacterAgencyShadowed)
                }

                "Victoria Housekeeping Co." -> {
                    Glide.with(view)
                        .load(R.drawable.victoria_housekeeping_co)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.victoria_housekeeping_co)
                        .into(imageViewCharacterAgencyShadowed)
                }

                "N.E.P.S." -> {
                    Glide.with(view)
                        .load(R.drawable.new_eridu_public_security)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.new_eridu_public_security)
                        .into(imageViewCharacterAgencyShadowed)
                }

                "Belobog Heavy Industries" -> {
                    Glide.with(view)
                        .load(R.drawable.belobog_heavy_industries)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.belobog_heavy_industries)
                        .into(imageViewCharacterAgencyShadowed)
                }

                "Section 6" -> {
                    Glide.with(view)
                        .load(R.drawable.section_6)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.section_6)
                        .into(imageViewCharacterAgencyShadowed)
                }

                "Sons of Calydon" -> {
                    Glide.with(view)
                        .load(R.drawable.sons_of_calydon)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.sons_of_calydon)
                        .into(imageViewCharacterAgencyShadowed)
                }

                "OBOLS Squad" -> {
                    Glide.with(view)
                        .load(R.drawable.obols_obsidian)
                        .into(imageViewCharacterFaction)
                    Glide.with(view)
                        .load(R.drawable.obols_obsidian)
                        .into(imageViewCharacterAgencyShadowed)
                }

                else -> {
//                    Glide.with(view)
//                        .load(R.drawable.sons_of_calydon)
//                        .into(imageViewCharacterFaction)
//                    Glide.with(view)
//                        .load(R.drawable.sons_of_calydon)
//                        .into(imageViewCharacterAgencyShadowed)
                }
            }
        }

        val imageViewCharacter = view.findViewById<ImageView>(R.id.imageViewCharacter)
        Glide.with(view)
            .load("https://www.prydwen.gg${character?.cardImage?.localFile?.childImageSharp?.gatsbyImageData?.images?.fallback?.src}")
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageViewCharacter)

        val imageViewCharacterSpeciality = view.findViewById<ImageView>(R.id.imageViewCharacterSpeciality)
        if (character?.style != null) {
            when (character.style.lowercase()) {
                    "attack" -> {
                        Glide.with(view)
                            .load(R.drawable.attack)
                            .into(imageViewCharacterSpeciality)
                    }

                    "stun" -> {
                        Glide.with(view)
                            .load(R.drawable.stun)
                            .into(imageViewCharacterSpeciality)
                    }

                    "anomaly" -> {
                        Glide.with(view)
                            .load(R.drawable.anomaly)
                            .into(imageViewCharacterSpeciality)
                    }

                    "support" -> {
                        Glide.with(view)
                            .load(R.drawable.support)
                            .into(imageViewCharacterSpeciality)
                    }

                    "defense" -> {
                        Glide.with(view)
                            .load(R.drawable.defense)
                            .into(imageViewCharacterSpeciality)
                    }

                    else -> {
                        Glide.with(view)
                            .load(R.drawable.stun)
                            .into(imageViewCharacterSpeciality)
                    }
                }
            }

            val imageViewRank = view.findViewById<ImageView>(R.id.imageViewRank)
            val viewCharacterSpecialityColor =
                view.findViewById<View>(R.id.viewCharacterSpecialityColor)
            if (character?.rarity != null) {

                when (character.rarity) {
                    "A" -> {
                        Glide.with(view)
                            .load(R.drawable.arank)
                            .into(imageViewRank)
                        viewCharacterSpecialityColor.setBackgroundColor(Color.parseColor("#AB3A4E"))
                    }

                    "S" -> {
                        Glide.with(view)
                            .load(R.drawable.srank)
                            .into(imageViewRank)
                        viewCharacterSpecialityColor.setBackgroundColor(Color.parseColor("#F9A700"))
                    }

                    else -> {
//                        Glide.with(view)
//                            .load(R.drawable.srank)
//                            .into(imageViewRank)
//                        viewCharacterSpecialityColor.setBackgroundColor(Color.parseColor("#F9A700"))
                    }
                }
            }

            view.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    view.background =
                        ContextCompat.getDrawable(contextCharacter, R.drawable.translucent_black)
                } else if (event.action == MotionEvent.ACTION_UP) {
                    view.background =
                        ContextCompat.getDrawable(contextCharacter, R.drawable.layout_bg)
                    clickListener(character)
                }
                true
            }
        }
    }
