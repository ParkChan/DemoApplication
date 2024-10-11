package com.example.demo.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.ActivityRecyclerviewTestBinding
import com.example.demo.databinding.ListItemBinding

/**
 * RecyclerView 테스트 화면
 */
class RecyclerViewTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerviewTestBinding

    private lateinit var adapter: MyAdapter
    private val itemList: MutableList<String> = mutableListOf()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecyclerviewTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.layoutManager = LinearLayoutManager(this)

        for (i in 1..30) {
            itemList.add("This is item number $i")
        }

        adapter = MyAdapter(itemList)
        binding.rvList.adapter = adapter

        startRefreshingList()
    }

    private fun startRefreshingList() {
        // 0.2초 간격으로 리스트 갱신
        var currentIndex = 0

        val updateTask = object : Runnable {
            override fun run() {
                if (currentIndex < itemList.size) {
                    adapter.notifyItemChanged(currentIndex)
                    currentIndex++
                    handler.postDelayed(this, 200) // 0.2초 지연
                }
            }
        }

        handler.post(updateTask)
    }

    // RecyclerView Adapter
    inner class MyAdapter(private val items: List<String>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemBinding.inflate(inflater, parent, false)

            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val itemText = items[position]
            holder.textView.text = itemText

            // TextView의 라인 수에 따라 버튼을 보이거나 숨김 처리
            holder.textView.post {
                val layout: Layout? = holder.textView.layout
                if (layout != null && layout.lineCount > 2) {
                    holder.button.visibility = View.GONE // 2줄 이상일 경우 버튼 숨김
                } else {
                    holder.button.visibility = View.VISIBLE // 2줄 이하일 경우 버튼 표시
                }
            }
        }

        override fun getItemCount(): Int = items.size

        inner class MyViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
            val textView: TextView = binding.textView
            val button: Button = binding.button

        }
    }
}


