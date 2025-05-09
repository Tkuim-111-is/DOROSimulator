package com.example.dorosimulator

import android.os.Bundle
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {

    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 接收從 MainActivity 傳來的 draw_result
        val drawResult = intent.getSerializableExtra("draw_result") as? ArrayList<Item>
        val gridView = findViewById<GridView>(R.id.gridview)

        // 設置GridView的適配器
        if (drawResult != null) {
            gridView.adapter = MyAdapter(this, drawResult, R.layout.adapter_horizontal)
        }
        // 綁定元件
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)

        // 一進來就開始跑進度
        showBlackOverlayAndStartDownload()
    }

    private fun showBlackOverlayAndStartDownload() {
        progressBar.progress = 0
        tvProgress.text = "0%"

        Thread {
            var progress = 0
            while (progress <= 100) {
                Thread.sleep(50)
                progress++
                runOnUiThread {
                    progressBar.progress = progress
                    tvProgress.text = "$progress%"
                }
            }

            // 進度條跑完後返回主畫面
            runOnUiThread {
                finish()  // 結束此Activity，回到MainActivity
            }
        }.start()
    }
}
