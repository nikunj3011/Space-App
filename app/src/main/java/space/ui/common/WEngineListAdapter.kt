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
import space.models.api.wengines.WEngineResponseItem

class WEngineListAdapter (
    private val contextWeapon: Context,
    private var weaponsList:List<WEngineResponseItem>,
    private val clickListener:(WEngineResponseItem)->Unit
    ) : RecyclerView.Adapter<MyViewHolder2>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem = layoutInflater.inflate(R.layout.layout_wengine_list,parent,false)
            return MyViewHolder2(listItem)
        }

        override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
            val weapon = weaponsList[position]
            holder.bind(contextWeapon, weapon, clickListener)
        }

        fun setFilteredList(mList: List<WEngineResponseItem>){
            this.weaponsList = mList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return weaponsList.size
        }

    }

    class MyViewHolder2(val view: View): RecyclerView.ViewHolder(view){
        @SuppressLint("ClickableViewAccessibility")
        fun bind(contextWeapon: Context, wengine: WEngineResponseItem, clickListener:(WEngineResponseItem)->Unit) {
            val myTextView = view.findViewById<TextView>(R.id.wengineName)
            myTextView.text = wengine.title
            val echoImageView = view.findViewById<ImageView>(R.id.imageViewWEngine)
            if(wengine.name != null) {
                Glide.with(view)
                    .load(wengine.icon_url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(echoImageView)
            }

            val imageViewWengineRank = view.findViewById<ImageView>(R.id.imageViewWengineRank)
            val viewWengineRank1 =
                view.findViewById<View>(R.id.viewWengineRank1)
            val viewWengineRank2 =
                view.findViewById<View>(R.id.viewWengineRank2)

            if (wengine?.categories?.elementAt(0) != null) {

                when (wengine.categories.elementAt(0).name) {
                    "a-rank" -> {
                        Glide.with(view)
                            .load(R.drawable.arank)
                            .into(imageViewWengineRank)
                        viewWengineRank1.setBackgroundColor(Color.parseColor("#AB3A4E"))
                        viewWengineRank2.setBackgroundColor(Color.parseColor("#AB3A4E"))
                    }

                    "s-rank" -> {
                        Glide.with(view)
                            .load(R.drawable.srank)
                            .into(imageViewWengineRank)
                        viewWengineRank1.setBackgroundColor(Color.parseColor("#F9A700"))
                        viewWengineRank2.setBackgroundColor(Color.parseColor("#F9A700"))
                    }

                    "b-rank" -> {
                        Glide.with(view)
                            .load(R.drawable.brank)
                            .into(imageViewWengineRank)
                        viewWengineRank1.setBackgroundColor(Color.parseColor("#0FB8E5"))
                        viewWengineRank2.setBackgroundColor(Color.parseColor("#0FB8E5"))
                    }

                    else -> {
                        Glide.with(view)
                            .load(R.drawable.srank)
                            .into(imageViewWengineRank)
                        viewWengineRank1.setBackgroundColor(Color.parseColor("#F9A700"))
                        viewWengineRank2.setBackgroundColor(Color.parseColor("#F9A700"))
                    }
                }
            }

            val imageViewWEngineSpeciality = view.findViewById<ImageView>(R.id.imageViewWEngineSpeciality)
            if (wengine?.categories?.elementAt(1) != null) {
                when (wengine.categories.elementAt(1).name.lowercase()) {
                    "attack" -> {
                        Glide.with(view)
                            .load(R.drawable.attack)
                            .into(imageViewWEngineSpeciality)
                    }

                    "stun" -> {
                        Glide.with(view)
                            .load(R.drawable.stun)
                            .into(imageViewWEngineSpeciality)
                    }

                    "anomaly" -> {
                        Glide.with(view)
                            .load(R.drawable.anomaly)
                            .into(imageViewWEngineSpeciality)
                    }

                    "support" -> {
                        Glide.with(view)
                            .load(R.drawable.support)
                            .into(imageViewWEngineSpeciality)
                    }

                    "defense" -> {
                        Glide.with(view)
                            .load(R.drawable.defense)
                            .into(imageViewWEngineSpeciality)
                    }

                    else -> {
                        Glide.with(view)
                            .load(R.drawable.stun)
                            .into(imageViewWEngineSpeciality)
                    }
                }
            }

            view.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    view.background =
                        ContextCompat.getDrawable(contextWeapon, R.drawable.translucent_black)
                } else if (event.action == MotionEvent.ACTION_UP) {
                    view.background =
                        ContextCompat.getDrawable(contextWeapon, R.drawable.layout_bg)
                    clickListener(wengine)
                }
                true
            }
        }
    }