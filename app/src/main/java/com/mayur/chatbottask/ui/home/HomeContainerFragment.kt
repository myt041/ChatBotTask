package com.mayur.chatbottask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mayur.chatbottask.R
import com.mayur.chatbottask.databinding.FragmentHomeContainerBinding

class HomeContainerFragment : Fragment() {

    private lateinit var binding: FragmentHomeContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_container, container, false)

        binding.imgMenu.setOnClickListener {
            openDrawer()
        }

        drawerLayoutListeners()

        return binding.root

    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun drawerLayoutListeners() {
        binding.drawerNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.user_1 -> {

                    val action = HomeContainerFragmentDirections.homeToChat(
                        "111", "Mayur"
                    )

                    findNavController().navigate(action)
                    true
                }

                R.id.user_2 -> {
                    val action = HomeContainerFragmentDirections.homeToChat(
                        "222", "User2"
                    )

                    findNavController().navigate(action)

                    true
                }
                R.id.user_3 -> {
                    val action = HomeContainerFragmentDirections.homeToChat(
                        "333", "User3"
                    )

                    findNavController().navigate(action)

                    true
                }
                R.id.user_4 -> {

                    val action = HomeContainerFragmentDirections.homeToChat(
                        "444", "User4"
                    )

                    findNavController().navigate(action)

                    true
                }
                R.id.user_5 -> {
                    val action = HomeContainerFragmentDirections.homeToChat(
                        "555", "User5"
                    )

                    findNavController().navigate(action)

                    true
                }
                else -> {

                    true
                }
            }
        }
    }

}