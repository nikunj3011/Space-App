package space.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
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
import space.models.api.character.PlanetResponseItem
import kotlin.math.round


class PlanetListAdapter(
    private val contextCharacter: Context,
    private var fruitsList:List<PlanetResponseItem>,
    private val clickListener:(PlanetResponseItem)->Unit
) : RecyclerView.Adapter<MyViewHolder3>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder3 {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.layout_planet_list,parent,false)
        return MyViewHolder3(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder3, position: Int) {
        val fruit = fruitsList[position]
        holder.bind(contextCharacter, fruit,clickListener)
    }

    fun setFilteredList(mList: List<PlanetResponseItem>){
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
        character: PlanetResponseItem,
        clickListener: (PlanetResponseItem) -> Unit
    ) {
        val characterTextView = view.findViewById<TextView>(R.id.characterName)
        characterTextView.text = character.englishName.uppercase()
        val textViewPlanetDescription = view.findViewById<TextView>(R.id.textViewPlanetDescription)
        textViewPlanetDescription.text = character.name.uppercase()
        val imageViewCharacter = view.findViewById<ImageView>(R.id.imageViewCharacter)
        Glide.with(view)
            .load(character?.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageViewCharacter)
        var moons = 0
        if(!character.moons.isNullOrEmpty())
        {
            moons = character.moons.size
        }
        val textViewPlanetMoons = view.findViewById<TextView>(R.id.textViewPlanetMoons)
        textViewPlanetMoons.text = "$moons CONFIRMED"
        val textViewPlanetsGravity = view.findViewById<TextView>(R.id.textViewPlanetsGravity)
        textViewPlanetsGravity.text = "${character.gravity}G"
        val textViewPlanetRadius = view.findViewById<TextView>(R.id.textViewPlanetRadius)
        textViewPlanetRadius.text = "${character.meanRadius} KMS"
        val textViewPlanetTemperature = view.findViewById<TextView>(R.id.textViewPlanetTemperature)
        textViewPlanetTemperature.text = "${round( character.avgTemp - 273.15)} \u2103"
//        val imageViewScroll = view.findViewById<ImageView>(R.id.imageViewScrollPlanet)
//        imageViewScroll.setImageResource(R.drawable.anime_bottom_container_check)
//        val avdCheckToClose = imageViewScroll.drawable as AnimatedVectorDrawable
//        avdCheckToClose.start()

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
