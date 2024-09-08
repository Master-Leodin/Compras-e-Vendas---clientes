package com.android.compras
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var editTextItem: EditText
    private lateinit var buttonAddItem: Button
    private lateinit var buttonClearList: Button
    private lateinit var buttonShareList: Button
    private lateinit var recyclerView: RecyclerView
    private val items = mutableListOf<Item>()
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando as views
        editTextItem = findViewById(R.id.editTextItem)
        buttonAddItem = findViewById(R.id.buttonAddItem)
        buttonClearList = findViewById(R.id.buttonClearList)
        buttonShareList = findViewById(R.id.buttonShareList)
        recyclerView = findViewById(R.id.recyclerView)

        // Configurando o RecyclerView
        adapter = ShoppingListAdapter(items, ::toggleItemStatus, ::removeItem)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Ações dos botões
        buttonAddItem.setOnClickListener {
            val itemName = editTextItem.text.toString()
            if (itemName.isNotEmpty()) {
                addItem(itemName)
                editTextItem.text.clear()
            }
        }

        buttonClearList.setOnClickListener {
            clearList()
        }

        buttonShareList.setOnClickListener {
            shareList()
        }
    }

    private fun addItem(name: String) {
        items.add(Item(name))
        adapter.notifyItemInserted(items.size - 1)
    }

    private fun toggleItemStatus(position: Int) {
        val item = items[position]
        item.isBought = !item.isBought
        adapter.notifyItemChanged(position)
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun clearList() {
        items.clear()
        adapter.notifyDataSetChanged()
    }

    private fun shareList() {
        val itemListString = items.joinToString("\n") { "${it.name} - ${if (it.isBought) "Comprado" else "Não Comprado"}" }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, itemListString)
        }
        startActivity(Intent.createChooser(intent, "Compartilhar Lista"))
    }
}