package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class History : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title= "HISTORY"
        }


        binding?.toolbarHistory?.setNavigationOnClickListener{
            onBackPressed()
        }

        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)

    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{allCompletedDatesList ->

                if (allCompletedDatesList.isNotEmpty()){
                    binding?.tvRecords?.visibility = View.VISIBLE
                    binding?.recycleviewitem?.visibility=View.VISIBLE
                    binding?.tvNorecord?.visibility= View.INVISIBLE

                    binding?.recycleviewitem?.layoutManager = LinearLayoutManager(this@History)
                    val dates = ArrayList<String>()
                    for(date in allCompletedDatesList){
                        dates.add(date.date)
                    }
                    val historyAdapter= HistoryAdapter(dates)
                    binding?.recycleviewitem?.adapter = historyAdapter


                }else{
                    binding?.tvRecords?.visibility = View.INVISIBLE
                    binding?.recycleviewitem?.visibility=View.INVISIBLE
                    binding?.tvNorecord?.visibility= View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}