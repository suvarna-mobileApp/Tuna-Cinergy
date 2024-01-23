package com.tuna.cinergy.ui.login

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import com.tuna.cinergy.R
import com.tuna.cinergy.business.domain.state.DataState
import com.tuna.cinergy.business.domain.utils.deviceId
import com.tuna.cinergy.business.domain.utils.deviceType
import com.tuna.cinergy.business.domain.utils.exhaustive
import com.tuna.cinergy.business.domain.utils.isOnline
import com.tuna.cinergy.business.domain.utils.loginType
import com.tuna.cinergy.business.domain.utils.secretKey
import com.tuna.cinergy.databinding.ActivityLoginBinding
import com.tuna.cinergy.ui.dashboard.DashBoardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var pd : Dialog
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pd = Dialog(this, android.R.style.Theme_Black)
        val view: View = LayoutInflater.from(this).inflate(R.layout.progress, null)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.getWindow()!!.setBackgroundDrawableResource(R.color.transparent)
        pd.setContentView(view)
        pd.show()

        subscribeObservers()

        binding.btn3.setOnClickListener (View.OnClickListener { view ->
            if(isOnline(this@LoginActivity)){
                pd.show()
                loginViewModel.guestLoginRequest(deviceId,deviceType,loginType)
            }else{
                Toast.makeText(this@LoginActivity,resources.getText(R.string.network), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun subscribeObservers() {
        loginViewModel.myLoginDataSTate.observe(this@LoginActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    Toast.makeText(this@LoginActivity,"Some error. Please try later", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    pd.dismiss()
                    Log.e("LOGIN::", "UI Details: ${dataState.data}")
                    loginViewModel.updateAccessToken(dataState.data.token.toString())
                }

                else -> {}
            }.exhaustive
        }

        loginViewModel.myGuestLoginDataSTate.observe(this@LoginActivity) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    pd.dismiss()
                    Toast.makeText(this@LoginActivity,"Some error. Please try later", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    pd.dismiss()
                    Log.e("Guest LOGIN::", "UI Details: ${dataState.data}")
                    gotoDashBoard(dataState.data.user.id)
                }

                else -> {}
            }.exhaustive
        }
    }

    private fun gotoDashBoard(userId:String?) {
        Intent(this@LoginActivity, DashBoardActivity::class.java).apply {
            intent.putExtra("userId", userId)
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if(isOnline(this@LoginActivity)){
            loginViewModel.loginRequest(secretKey,deviceType,deviceId,"")
        }else{
            Toast.makeText(this@LoginActivity,resources.getText(R.string.network), Toast.LENGTH_SHORT).show()
        }
    }
}