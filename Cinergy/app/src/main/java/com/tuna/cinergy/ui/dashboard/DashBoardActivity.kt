package com.tuna.cinergy.ui.dashboard

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuna.cinergy.R
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.utils.deviceId
import com.tuna.cinergy.business.domain.utils.deviceType
import com.tuna.cinergy.business.domain.utils.exhaustive
import com.tuna.cinergy.business.domain.utils.isOnline
import com.tuna.cinergy.business.domain.utils.locationId
import com.tuna.cinergy.business.domain.utils.loginType
import com.tuna.cinergy.databinding.ActivityDashBoardBinding
import com.tuna.cinergy.databinding.ActivityLoginBinding
import com.tuna.cinergy.ui.dashboard.adapter.EscapeRoomRecyclerAdapter
import com.tuna.cinergy.ui.movieinfo.MovieInfoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dash_board.view.recycler_room

@AndroidEntryPoint
class DashBoardActivity : AppCompatActivity(),EscapeRoomRecyclerAdapter.OnItemClickListener{
    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var pd : Dialog
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var escapeRoomRecyclerAdapter : EscapeRoomRecyclerAdapter
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(R.layout.progress, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(R.color.transparent)
        pd.setContentView(view)
        pd.show()

        userId = intent.getStringExtra("userId").toString()

        binding.textBack.setOnClickListener (View.OnClickListener { view ->
            finish()
        })
        subscribeObservers()
    }

    private fun subscribeObservers() {
        dashboardViewModel.myRoomDataSTate.observe(this@DashBoardActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    Toast.makeText(this@DashBoardActivity,"Some error. Please try later", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    pd.dismiss()
                    Log.e("LOGIN::", "UI Details: ${dataState.data}")
                    binding.recyclerRoom.apply {
                        escapeRoomRecyclerAdapter = EscapeRoomRecyclerAdapter(dataState.data,this@DashBoardActivity,this@DashBoardActivity)
                        layoutManager = GridLayoutManager(this@DashBoardActivity,2)
                        adapter = escapeRoomRecyclerAdapter
                    }
                }

                else -> {}
            }.exhaustive
        }
    }

    @Override
    override fun onResume() {
        super.onResume()
        if(isOnline(this@DashBoardActivity)){
            dashboardViewModel.escapeRoomRequest(userId,deviceId,"2",deviceType,locationId)
        }else{
            Toast.makeText(this@DashBoardActivity,resources.getText(R.string.network), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClick(title:String,runTime:String,synopsis:String,ticket:String,imageUrl: String,movieId: String) {
        val bottomSheet = BottomSheetDialog(title,runTime,synopsis,ticket,imageUrl,movieId,userId)
        bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
    }

    class BottomSheetDialog(val title:String,val runTime:String,val synopsis:String, val ticket:String,val imageUrl:String,val movieId: String,val userId: String) : BottomSheetDialogFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

            val Button = view.findViewById<Button>(R.id.btn_1)
            val txtTitle = view.findViewById<TextView>(R.id.txt_title)
            val textTime = view.findViewById<TextView>(R.id.text_time)
            val textMember = view.findViewById<TextView>(R.id.text_member)
            val imgCancel = view.findViewById<ImageView>(R.id.img_cancel)
            val textContent = view.findViewById<TextView>(R.id.text_content)
            val imgPoster = view.findViewById<ImageView>(R.id.img_poster)

            txtTitle.setText(title)
            textTime.setText("$runTime mins")
            textMember.setText(ticket)
            textContent.setText(synopsis)
            Glide.with(this)
                .load(imageUrl)
                .into(imgPoster)

            Button.setOnClickListener {
                Intent(requireActivity(), MovieInfoActivity::class.java).apply {
                    putExtra("movieId", movieId)
                    putExtra("userId", userId)
                    startActivity(this)
                }
                dismiss()
            }

            imgCancel.setOnClickListener {
                dismiss()
            }

            return view
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