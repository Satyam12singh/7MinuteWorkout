package com.example.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root) {
        val tvitem = binding.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel = items[position]
        holder.tvitem.text = model.getId().toString()


        when{
            model.getIsSelected() ->{
                holder.tvitem.background= ContextCompat.getDrawable(holder.itemView.context, R.drawable.recycle_view_background)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))
            }

            model.getIsCompleted() -> {
                holder.tvitem.background= ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_background)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))

            }
            else -> {
                holder.tvitem.background= ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_background)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }
}