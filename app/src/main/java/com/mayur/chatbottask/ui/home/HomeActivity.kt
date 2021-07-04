package com.mayur.chatbottask.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.mayur.chatbottask.R
import com.mayur.chatbottask.databinding.ActivityHomeBinding
import com.mayur.chatbottask.ui.chat.ChatViewModel
import com.rommansabbir.networkx.isInternetConnected
import com.rommansabbir.networkx.isInternetConnectedLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    private var lastStatus = false
    private val viewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

//        lastStatus = isInternetConnected()
//       isInternetConnectedLiveData().observe(this,{
//           if(it != lastStatus){
//               lastStatus = it
//               if(lastStatus){
//
//                   viewModel.performNetworkBackOperation()
//
//               }
//           }
//       })

    }


}