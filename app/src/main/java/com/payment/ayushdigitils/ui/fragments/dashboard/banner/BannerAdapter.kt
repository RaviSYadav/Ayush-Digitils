package com.payment.ayushdigitils.ui.fragments.dashboard.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ui.dashboard.banner.SliderItems
import com.payment.ayushdigitils.databinding.ViewHolderItemBannerBinding


class BannerAdapter  constructor(
    private val sliderItems: MutableList<SliderItems>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<BannerAdapter.SliderViewHolder?>() {

    private lateinit var binding: ViewHolderItemBannerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {

        binding = ViewHolderItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder( itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun setImage(sliderItems: SliderItems) {
            binding.imgIcon.setImageResource(sliderItems.image);
}
}
    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

}

