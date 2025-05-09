package com.example.dorosimulator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dorosimulator.Item

class MainActivity : AppCompatActivity() {
    val draw_result = arrayListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gridView = findViewById<GridView>(R.id.gridview)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val btnspin = findViewById<Button>(R.id.btnspin)


        val count = ArrayList<String>().apply {
            add("1抽")
            add("10抽")
        }

        val item = ArrayList<Item>()
        val quantity = 0
        val array = resources.obtainTypedArray(R.array.image_list)
        for (index in 0 until array.length()) {
            val photo = array.getResourceId(index, 0)
            val name = "DORO${index + 1}號"
            item.add(Item(photo, name, quantity))
        }
        array.recycle()

        // 設置GridView的適配器
        gridView.adapter = MyAdapter(this, item, R.layout.adapter_horizontal)

        // 設置Spinner的適配器
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, count)

        // 設置Spinner的選擇監聽
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position < count.size) {
                    // 根據選擇的項目啟用/禁用按鈕
                    btnspin.isEnabled = count[position] == "1抽" || count[position] == "10抽"
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // 不需要處理
            }
        })

        // 按鈕點擊事件 - 執行抽獎操作
        btnspin.setOnClickListener {
            val drawCount = when (spinner.selectedItem) {
                "1抽" -> 1 // 1次抽獎
                "10抽" -> 10 // 10次抽獎
                else -> 0
            }

            if (drawCount > 0) {
                // 執行抽獎
                performDraw(item, drawCount)

                // 顯示Toast告訴用戶抽了多少次
                Toast.makeText(this, "本次抽獎次數：$drawCount", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, SecActivity::class.java)
                intent.putExtra("draw_result", draw_result)
                startActivity(intent)
                // 更新GridView顯示
//                gridView.adapter?.notifyDataSetChanged()
            }

        }
    }


    // 抽獎邏輯：根據抽獎次數更新quantity
    private fun performDraw(items: ArrayList<Item>, drawCount: Int) {
        repeat(drawCount) {
            // 隨機選擇一個Item
            val randomIndex = (0 until items.size).random()
            val selectedItem = items[randomIndex]
            draw_result.add(selectedItem)
            // 增加該項目的quantity
            selectedItem.quantity += 1
        }
    }
}



