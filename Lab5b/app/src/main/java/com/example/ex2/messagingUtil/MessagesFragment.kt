package com.example.ex2.messagingUtil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ex2.UserActivity
import com.example.ex2.UsersListViewModel
import com.example.ex2.databinding.FragmentMessagesBinding

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UsersListViewModel
    private lateinit var adapter : UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = UsersAdapter(this.requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessagesBinding.inflate(inflater, container,false)
        viewModel = ViewModelProviders.of(this).get(UsersListViewModel::class.java)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this.context)
        viewModel.usersList.observe(viewLifecycleOwner, Observer {
            adapter.addUser(it)
        })
        viewModel.getRealTimeUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}