package com.example.basicapp.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicapp.R
import com.example.basicapp.databinding.FragmentUserListBinding
import com.example.basicapp.data.model.GithubUser
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

        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.updateData(users)
        }
    }

    override fun onUserClick(user: GithubUser) {
        val detailFragment = UserDetailFragment.newInstance(
            login = user.login,
            avatarUrl = user.avatar_url,
            htmlUrl = user.html_url,
            type = user.type
        )

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
