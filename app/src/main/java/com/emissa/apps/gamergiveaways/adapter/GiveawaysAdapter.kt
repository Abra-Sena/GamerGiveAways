package com.emissa.apps.gamergiveaways.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emissa.apps.gamergiveaways.R
import com.emissa.apps.gamergiveaways.databinding.GiveawaysItemBinding
import com.emissa.apps.gamergiveaways.model.Giveaways
import com.squareup.picasso.Picasso

class GiveawaysAdapter(
    private val giveaways: MutableList<Giveaways> = mutableListOf()
): RecyclerView.Adapter<GiveAwayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveAwayViewHolder {
        return GiveAwayViewHolder(
            GiveawaysItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GiveAwayViewHolder, position: Int) {
        val giveaway = giveaways[position]
        holder.bind(giveaway)
    }

    override fun getItemCount(): Int = giveaways.size

    fun setNewGiveaways(newGiveaways: List<Giveaways>) {
        giveaways.clear()
        giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }
}

class GiveAwayViewHolder(
    private val binding: GiveawaysItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(giveaways: Giveaways) {
        binding.giveawayDate.text = giveaways.endDate
        binding.giveawayPlatform.text = giveaways.platforms
        binding.giveawayTitle.text = giveaways.title
        binding.giveawayWorth.text = giveaways.worth
        binding.giveawayStatus.text = giveaways.status

        Picasso.get()
            .load(giveaways.image)
            .placeholder(R.drawable.ic_baseline_camera_loading)
            .error(R.drawable.ic_baseline_broken_image_error)
            .fit()
            .into(binding.giveawayImage)
    }

}