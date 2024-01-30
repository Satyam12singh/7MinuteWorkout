package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkout.databinding.ActivityFinishedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishedActivity : AppCompatActivity() {


    private var binding:ActivityFinishedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FinishedActivity", "onCreate called")
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishedActivity)

        // Check if the ActionBar is not null before setting up navigation
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            binding?.toolbarFinishedActivity?.setNavigationOnClickListener {
                onBackPressed()
            }

        }

        binding?.tvTwo?.text = "You Have Done The Workout!!"

        binding?.buttonFinished?.setOnClickListener {
            val intent = Intent (this@FinishedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dao= (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        val c= Calendar.getInstance()
        val datetime= c.time

        Log.e("Date: ",""+datetime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date= sdf.format(datetime)

        Log.e("Formated date",""+date)

        lifecycleScope.launch(Dispatchers.IO) {
            historyDao.insert(HistoryEntity(date))
        }

    }


}