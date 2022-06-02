package com.example.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityItemTouchHelperBinding

/**
 * 리스트 아이템을 터치하여 이동하는 예제
 * TODO : https://github.com/dhtmaks2540/ItemTouchHelperExample
 */
class ItemTouchHelperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemTouchHelperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemTouchHelperBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}