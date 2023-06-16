package com.example.myapplication.presentation.showdetailsscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.model.User
import com.example.myapplication.databinding.FragmentListUsersBinding
import com.example.myapplication.presentation.createscreen.MainViewModel
import com.example.myapplication.presentation.utils.Resources

class ListUsersFragment : Fragment() {
    private lateinit var binding: FragmentListUsersBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var adapter: UsersAdapter
    private var usersList: ArrayList<User> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_users, container, false)
        mViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.apply {
            mainViewModel = mViewModel
            lifecycleOwner = this@ListUsersFragment
            recycler.layoutManager = LinearLayoutManager(context)
        }
        binding.root.setOnClickListener {}
        lifecycleScope.launchWhenCreated {
            mViewModel.getAllUsers().observe(viewLifecycleOwner) { users ->
                users.forEach {
                    usersList.add(
                        User(
                            age = it.userAge,
                            gender = it.userGender,
                            jobTitle = it.userJobTitle,
                            name = it.userName
                        )
                    )
                }
                adapter = UsersAdapter(usersList)
                binding.recycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            mViewModel.mainFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { commands ->
                    when (commands) {
                        is Resources.CustomCommands.FinishListFragment -> {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .remove(this@ListUsersFragment).commit()
                        }

                        else -> {

                        }
                    }
                }
        }
        return binding.root
    }

}