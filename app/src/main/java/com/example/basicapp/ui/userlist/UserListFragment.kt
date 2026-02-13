package com.example.basicapp.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicapp.R
import com.example.basicapp.data.model.GithubUser
import com.example.basicapp.databinding.FragmentUserListBinding
import com.example.basicapp.ui.userdetail.UserDetailFragment

class UserListFragment : Fragment(), OnUserClickListener {
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, 
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserAdapter(this)
        binding.userRecycler.adapter = adapter

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // sorting
        val sortOptions = listOf("Sort by Name", "Sort by ID")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerSortFragment.adapter = spinnerAdapter

        // Load saved preference and set spinner position
        val savedOption = viewModel.getSavedSortOption()
        val savedPosition = sortOptions.indexOf(savedOption)
        if (savedPosition != -1) {
            binding.spinnerSortFragment.setSelection(savedPosition, false)
        }

        var isSpinnerInitialized = false
        binding.spinnerSortFragment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Only process spinner changes after initialization is complete
                if (isSpinnerInitialized) {
                    val option = sortOptions[position]
                    viewModel.setSortOption(option)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) = Unit
        }

        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.updateData(users)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.userRecycler.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        // Mark spinner as initialized after observers are set up
        isSpinnerInitialized = true
    }

    override fun onUserClick(user: GithubUser) {
        val detailFragment = UserDetailFragment.newInstance(user)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }


    fun scrollToTop() {
        binding.userRecycler.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
