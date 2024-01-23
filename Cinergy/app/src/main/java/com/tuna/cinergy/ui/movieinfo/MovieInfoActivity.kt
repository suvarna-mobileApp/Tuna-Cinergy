package com.tuna.cinergy.ui.movieinfo

import android.R
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.GridView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.utils.deviceId
import com.tuna.cinergy.business.domain.utils.deviceType
import com.tuna.cinergy.business.domain.utils.exhaustive
import com.tuna.cinergy.business.domain.utils.locationId
import com.tuna.cinergy.databinding.ActivityMovieInfoBinding
import com.tuna.cinergy.datasource.network.models.movieinfo.Sessions
import com.tuna.cinergy.datasource.network.models.movieinfo.ShowTime
import com.tuna.cinergy.ui.movieinfo.adapter.MovieDateRecyclerAdapter
import com.tuna.cinergy.ui.movieinfo.adapter.MovieTimeRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieInfoActivity : AppCompatActivity(),MovieDateRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMovieInfoBinding
    private val movieInfoViewModel: MovieInfoViewModel by viewModels()
    private lateinit var movieDateRecyclerAdapter : MovieDateRecyclerAdapter
    private lateinit var movieTimeRecyclerAdapter : MovieTimeRecyclerAdapter
    private lateinit var pd : Dialog
    private lateinit var userId : String
    private lateinit var movieId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()
        movieId = intent.getStringExtra("movieId").toString()

        pd = Dialog(this, R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(com.tuna.cinergy.R.layout.progress, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(com.tuna.cinergy.R.color.transparent)
        pd.setContentView(view)
        pd.show()

        binding.textBack.setOnClickListener (View.OnClickListener { view ->
            finish()
        })

        subscribeObservers()
    }

    private fun subscribeObservers() {
        movieInfoViewModel.myRoomDataSTate.observe(this@MovieInfoActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    Toast.makeText(this@MovieInfoActivity,"Some error. Please try later", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    pd.dismiss()
                    Log.e("LOGIN::", "UI Details: ${dataState.data}")
                    binding.txtTitle.setText(dataState.data.movieInfo.Title)
                    binding.txtRuntime.setText(" " + dataState.data.movieInfo.Rating + "  " + dataState.data.movieInfo.RunTime + " mins")
                    Glide.with(binding.root.context)
                        .load(dataState.data.movieInfo.image_url)
                        .into(binding.imgPoster)

                    binding.recyclerDate.apply {
                        movieDateRecyclerAdapter = MovieDateRecyclerAdapter(dataState.data.movieInfo.date_list,this@MovieInfoActivity,
                            this@MovieInfoActivity,dataState.data.movieInfo.showTime)
                        layoutManager = LinearLayoutManager(this@MovieInfoActivity,LinearLayoutManager.HORIZONTAL,
                            true)
                        adapter = movieDateRecyclerAdapter
                    }
                }

                else -> {}
            }.exhaustive
        }
    }

    override fun onResume() {
        super.onResume()
        movieInfoViewModel.movieInfoRequest(userId,deviceId,deviceType, locationId,movieId)
    }

    override fun onItemClick(
        date: String,
        time: List<Sessions>
    ) {
        binding.recyclerTime.apply {
            movieTimeRecyclerAdapter = MovieTimeRecyclerAdapter(this@MovieInfoActivity,time)
            layoutManager = GridLayoutManager(this@MovieInfoActivity,3)
            adapter = movieTimeRecyclerAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}