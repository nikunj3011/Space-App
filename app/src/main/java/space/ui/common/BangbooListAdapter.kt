package space.ui.common

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import space.R
import space.models.api.bangboo.BangBoosResponseItem


class BangbooListAdapter(
    private val contextEcho: Context,
    private var fruitsList:List<BangBoosResponseItem>,
    private val clickListener:(BangBoosResponseItem)->Unit
) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.layout_bangboo_list,parent,false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = fruitsList[position]
        holder.bind(contextEcho, fruit, clickListener)
    }

    fun setFilteredList(mList: List<BangBoosResponseItem>){
        this.fruitsList = mList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return fruitsList.size
    }

}

class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
    fun bind(contextEcho: Context, bangboo: BangBoosResponseItem, clickListener:(BangBoosResponseItem)->Unit) {
        val echoTextView = view.findViewById<TextView>(R.id.bangbooName)
        echoTextView.text = bangboo?.nick_name

        val textBangbooDes = view.findViewById<TextView>(R.id.textBangbooDes)
        textBangbooDes.text = bangboo?.bio

        val imageViewBangboo = view.findViewById<ImageView>(R.id.imageViewBangboo)
        Glide.with(view)
            .load("https://cdn.jsdelivr.net/gh/boringcdn/zzz/bangboos/${bangboo.name}.webp")
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageViewBangboo)

        var bangbooSkill1 = SpannableStringBuilder()
        if(bangboo.skills.isNotEmpty()){
            for (skill in bangboo.skills){
                bangbooSkill1 = bangbooSkill1.append()
                    .color(Color.WHITE, { append(skill?.name) })
                    .append(" : ")
                    .bold{ append(skill?.desc)
                    .append("\n")}
            }
        }
        val textViewBangbooSkill1 = view.findViewById<TextView>(R.id.textViewBangbooSkill1)
        textViewBangbooSkill1.text = bangbooSkill1

        var bangbooStats = "Stats- HP: ${bangboo.stats.HP}  ATK: ${bangboo.stats.ATK}  DEF: ${bangboo.stats.DEF}  Impact: ${bangboo.stats.Impact}"
        val textBangbooStats = view.findViewById<TextView>(R.id.textBangbooStats)
        textBangbooStats.text = bangbooStats

        val imageViewBangbooRank = view.findViewById<ImageView>(R.id.imageViewBangbooRank)
        val viewBangbooRank =
            view.findViewById<View>(R.id.viewBangbooRank)
        if (bangboo?.categories?.elementAt(0) != null) {

            when (bangboo.categories.elementAt(0).name) {
                "a-rank" -> {
                    Glide.with(view)
                        .load(R.drawable.arank)
                        .into(imageViewBangbooRank)
                    viewBangbooRank.setBackgroundColor(Color.parseColor("#AB3A4E"))
                }

                "s-rank" -> {
                    Glide.with(view)
                        .load(R.drawable.srank)
                        .into(imageViewBangbooRank)
                    viewBangbooRank.setBackgroundColor(Color.parseColor("#F9A700"))
                }

                else -> {
                    Glide.with(view)
                        .load(R.drawable.srank)
                        .into(imageViewBangbooRank)
                    viewBangbooRank.setBackgroundColor(Color.parseColor("#F9A700"))
                }
            }
        }

    }

}