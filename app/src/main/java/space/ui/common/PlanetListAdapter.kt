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


class PlanetListAdapter(
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

        val imageViewCharacter = view.findViewById<ImageView>(R.id.imageViewCharacter)
        Glide.with(view)
            .load(character?.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageViewCharacter)

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
