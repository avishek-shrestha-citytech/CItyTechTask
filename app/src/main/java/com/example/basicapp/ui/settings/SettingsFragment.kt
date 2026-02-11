package com.example.basicapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicapp.R
import com.example.basicapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val settingsAdapter = SettingsAdapter(
            listOf(
                Triple("Profile", R.drawable.ic_profile, 1),
                Triple("Change Password", R.drawable.ic_password, 2),
                Triple("Logout", R.drawable.ic_logout, 3)
            )
        ) { id ->
            when (id) {
                1 -> Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(requireContext(), "Change Password clicked", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(requireContext(), "Logout clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.settingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.settingsRecyclerView.adapter = settingsAdapter

        // Divider line
        binding.settingsRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
