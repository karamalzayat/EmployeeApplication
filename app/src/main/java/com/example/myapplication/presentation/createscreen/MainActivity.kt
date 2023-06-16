package com.example.myapplication.presentation.createscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.showdetailsscreen.ListUsersFragment
import com.example.myapplication.presentation.utils.Resources

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private var genderList = arrayListOf("Male", "Female", "NoAnswer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        mViewModel = ViewModelProvider(this)[MainViewModel(application)::class.java]
        binding.mainViewModel = mViewModel
        val userGenderArrayAdapter =
            ArrayAdapter(this, R.layout.spinner_text_item, genderList)
        userGenderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genderSpinner.adapter = userGenderArrayAdapter
        binding.genderSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position >= 0) mViewModel.selectedGender = genderList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        lifecycleScope.launchWhenStarted {
            mViewModel.mainFlow.flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { commands ->
                    when (commands) {
                        is Resources.CustomCommands.CreateNewUserFailed -> {
                            Toast.makeText(
                                this@MainActivity,
                                "please, Try again!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is Resources.CustomCommands.CreateNewUserFillRequirments -> {
                            Toast.makeText(
                                this@MainActivity,
                                "please, fill all the requirements!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is Resources.CustomCommands.CreateNewUserPressed -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Done Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.root_container, ListUsersFragment())
                                .commit()
                        }

                        else -> {}
                    }
                }
        }

    }
}