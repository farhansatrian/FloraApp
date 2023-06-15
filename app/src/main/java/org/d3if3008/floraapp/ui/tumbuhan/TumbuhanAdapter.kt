package org.d3if3008.floraapp.ui.tumbuhan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3008.floraapp.R
import org.d3if3008.floraapp.databinding.ListItemBinding
import org.d3if3008.floraapp.model.Tumbuhan
import org.d3if3008.floraapp.network.TumbuhanApi

class TumbuhanAdapter: RecyclerView.Adapter<TumbuhanAdapter.ViewHolder>() {
    private val data = mutableListOf<Tumbuhan>()

    fun updateData(newData: List<Tumbuhan>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tumbuhan: Tumbuhan) = with(binding) {
            namaTextView.text = tumbuhan.nama
            latinTextView.text = tumbuhan.namaLatin
            Glide.with(imageView.context)
                .load(TumbuhanApi.getTumbuhanUrl(tumbuhan.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)

            root.setOnClickListener {
                val message = root.context.getString(R.string.message, tumbuhan.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}