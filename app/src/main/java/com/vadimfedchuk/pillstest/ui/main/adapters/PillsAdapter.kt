package com.vadimfedchuk.pillstest.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vadimfedchuk.pillstest.R
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills
import com.vadimfedchuk.pillstest.ui.main.pojo.ResultsItem

class PillsAdapter(var pills: ArrayList<Pills>, val onClickItem:(id: Int) -> Unit) : RecyclerView.Adapter<PillsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_list_pill, parent, false))
            .listenOnClick{ position, type ->
                onClickItem(pills[position].idPill!!)
            }
    }

    override fun getItemCount() = pills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pills[position]
        holder.bind(item)
    }

    fun setData(list: ArrayList<Pills>) {
        //pills.clear()
        pills.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var title_trade: TextView
        private var title_manufacturer: TextView

        init {
            title_trade = view.findViewById(R.id.title_trade_tv)
            title_manufacturer = view.findViewById(R.id.title_manufacturer_tv)
        }

        fun bind(item: Pills) {
            title_trade.text = item.labelTrade ?: "name not found"
            title_manufacturer.text = item.nameManufacturer ?: "name not found"
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listenOnClick(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }
}