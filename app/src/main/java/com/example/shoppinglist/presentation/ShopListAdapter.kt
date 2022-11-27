package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallBack()) {

    var onShopItemOnLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemOnClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            ITEM_ENABLED -> R.layout.item_shop_enabled
            ITEM_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvCount.text = shopItem.count.toString()
        holder.tvName.text = shopItem.name
        holder.view.setOnLongClickListener {
            onShopItemOnLongClickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemOnClickListener?.invoke(shopItem)
            true
        }
    }

    private fun Boolean.toInt(): Int {
        return if (this) {
            ITEM_ENABLED
        } else {
            ITEM_DISABLED
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return shopItem.enabled.toInt()
    }

    companion object {
        val ITEM_ENABLED = 1
        val ITEM_DISABLED = 0
        val POOL_COUNT = 15
    }

}