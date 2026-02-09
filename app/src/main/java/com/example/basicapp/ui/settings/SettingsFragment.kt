package com.example.basicapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.R
import com.example.basicapp.model.SettingItem

class SettingsFragment : Fragment() {

    private lateinit var settingsRecyclerView: RecyclerView
    private lateinit var settingsAdapter: SettingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        settingsRecyclerView = view.findViewById(R.id.settingsRecyclerView)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val settingItems = listOf(
            SettingItem(1, "Profile", R.drawable.ic_profile),
            SettingItem(2, "Change Password", R.drawable.ic_password),
            SettingItem(3, "Logout", R.drawable.ic_logout)
        )

        settingsAdapter = SettingsAdapter(settingItems) { item ->
            handleSettingClick(item)
        }

        settingsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = settingsAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun handleSettingClick(item: SettingItem) {
        when (item.id) {
            1 -> {
                Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            2 -> {
                Toast.makeText(requireContext(), "Change Password clicked", Toast.LENGTH_SHORT).show()
            }
            3 -> {
                Toast.makeText(requireContext(), "Logout clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
