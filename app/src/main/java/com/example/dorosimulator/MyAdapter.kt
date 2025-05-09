package com.example.dorosimulator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    private val context: Context,
    private val data: ArrayList<Item>,
    private val layout: Int
) : BaseAdapter() {

    // 返回項目數量
    override fun getCount(): Int {
        return data.size
    }

    // 返回指定位置的項目
    override fun getItem(position: Int): Any {
        return data[position]
    }

    // 返回項目的ID
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // 這個方法用來顯示每個項目的UI
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layout, parent, false)

        val item = getItem(position) as Item

        // 找到圖片和文字的View
        val imgPhoto: ImageView = view.findViewById(R.id.imgPhoto)
        val tvMsg: TextView = view.findViewById(R.id.tvMsg)

        // 顯示圖片
        imgPhoto.setImageResource(item.photo)

        // 顯示名稱和數量
        tvMsg.text = "${item.name}: 抽獎次數：${item.quantity}"

        return view
    }
}

