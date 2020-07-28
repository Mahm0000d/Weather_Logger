package com.example.weatherlogger.ui.main
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlogger.R
import com.example.weatherlogger.data.model.WeatherItem
import com.example.weatherlogger.databinding.WeaterItemBinding
import com.example.weatherlogger.databinding.WeatherFirstItemBinding
import com.example.weatherlogger.ui.detail.DetailsActivity
import com.example.weatherlogger.utils.DateFormatter

class WeatherAdapter (context: AppCompatActivity): RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    private val CELL_TYPE_HEADER = 0
    private val CELL_TYPE_REGULAR_ITEM = 1
    private var data:MutableList<WeatherItem>
    private var context:AppCompatActivity?=null
    private var currentPosition:Int=0
    private var weather: Weather? =null

    init {
        data= mutableListOf()
        this.context=context
    }

    fun setData(data:List<WeatherItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
    inner class WeatherHolder
         (itemView: ViewDataBinding?) : RecyclerView.ViewHolder(itemView!!.root),View.OnLongClickListener,View.OnClickListener {
          var itemBinding: ViewDataBinding?= itemView
        init {
            when(itemBinding){
                is WeatherFirstItemBinding->{
                    var firstItemBinding=  itemBinding as WeatherFirstItemBinding
                    firstItemBinding.mainCard.setOnLongClickListener(this)
                    firstItemBinding.moreTv.setOnClickListener(this)
                }
                is WeaterItemBinding->{
                    var itemBinding=  itemBinding as WeaterItemBinding
                    itemBinding.mainCard.setOnLongClickListener(this)
                    itemBinding.moreTv.setOnClickListener(this)
                }
            }



        }
         override fun onLongClick(v: View?): Boolean {
             when(v!!.id){
                 R.id.main_card->{
                     weather!!.setLocation(data.get(adapterPosition).coord.lat,data.get(adapterPosition).coord.lon)
                     weather!!.setId(data.get(adapterPosition).id)
                     weather!!.openBottomSheet()
                 }

             }
             return true
         }

        override fun onClick(p0: View?) {
            when(p0!!.id){
                R.id.more_tv->{
                   var i:Intent = Intent(context,
                       DetailsActivity::class.java)
                    i.putExtra("cityId",data.get(adapterPosition).id)
                    context!!.startActivity(i)
                }
            }
        }
        //         private var weaterItemBinding: WeaterItemBinding?=null
//         constructor(itemView:WeaterItemBinding):super(itemView.root){
//             weaterItemBinding=itemView
//         }

     }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            CELL_TYPE_HEADER
        } else {
            CELL_TYPE_REGULAR_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding?=null
        when (viewType) {
            CELL_TYPE_HEADER -> {
                binding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.weather_first_item,
                    parent,
                    false
                )
              //  binding as WeatherFirstItemBinding
            }
            CELL_TYPE_REGULAR_ITEM -> {
                binding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.weater_item,
                    parent,
                    false
                )

               // binding as WeaterItemBinding
            }

        }
        return WeatherHolder(binding);
    }

    override fun getItemCount(): Int {
      return  data.size
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        currentPosition=position
       when(holder.getItemViewType()){
           CELL_TYPE_HEADER->{
              val weatherFirstItemBinding :WeatherFirstItemBinding= holder.itemBinding as WeatherFirstItemBinding
               weatherFirstItemBinding.mainCard.animation=
                   AnimationUtils.loadAnimation(
                       context,
                       R.anim.fade_scale_animation
                   )
                   weatherFirstItemBinding.nameTv.text=data.get(position).name
                   weatherFirstItemBinding.tempTv.text=data.get(position).main.temp.toString()
                   weatherFirstItemBinding.dateTv.text=DateFormatter.formatDate(data.get(position).date!!)

             //  weatherFirstItemBinding.mainCard.setOnLongClickListener(View.OnLongClickListener { v: View? ->  })
               //TODO : caluculate last update
           }
           CELL_TYPE_REGULAR_ITEM->{
               val weatherItemBinding :WeaterItemBinding= holder.itemBinding as WeaterItemBinding
               weatherItemBinding.mainCard.animation=
                   AnimationUtils.loadAnimation(
                       context,
                       R.anim.fade_scale_animation
                   )
               weatherItemBinding.nameTv.text=data.get(position).name
               weatherItemBinding.tempTv.text=data.get(position).main.temp.toString()
               weatherItemBinding.dateTv.text=DateFormatter.formatDate(data.get(position).date!!)
           }
       }
    }



    interface Weather{
        fun setLocation(lat:Double,lang:Double)
        fun setId(id:Int)
        fun openBottomSheet()
    }

   fun setWeatherListner(weather: Weather?){
       this.weather=weather
   }

}