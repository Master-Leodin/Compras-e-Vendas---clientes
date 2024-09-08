package com.android.compras
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(
    private val items: MutableList<Item>,
    private val onItemClicked: (Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClicked, onDeleteClicked)
    }

    override fun getItemCount(): Int = items.size

    class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val checkBoxBought: CheckBox = itemView.findViewById(R.id.checkBoxBought)
        private val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(
            item: Item,
            onItemClicked: (Int) -> Unit,
            onDeleteClicked: (Int) -> Unit
        ) {
            textViewName.text = item.name
            checkBoxBought.isChecked = item.isBought

            checkBoxBought.setOnCheckedChangeListener { _, _ ->
                onItemClicked(adapterPosition)
            }

            buttonDelete.setOnClickListener {
                onDeleteClicked(adapterPosition)
            }
        }
    }
}