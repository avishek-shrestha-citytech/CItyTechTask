package com.example.basicapp.ui.userdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil3.load
import com.example.basicapp.databinding.FragmentUserDetailBinding
import com.example.basicapp.ui.main.MainActivity
import androidx.core.net.toUri

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_LOGIN = "login"
        private const val ARG_AVATAR = "avatar_url"
        private const val ARG_HTML = "html_url"
        private const val ARG_TYPE = "type"

        fun newInstance(login: String, avatarUrl: String, htmlUrl: String, type: String): UserDetailFragment {
            val fragment = UserDetailFragment()
            val args = Bundle()  // Bundle stores key-value pairs
            args.putString(ARG_LOGIN, login)
            args.putString(ARG_AVATAR, avatarUrl)
            args.putString(ARG_HTML, htmlUrl)
            args.putString(ARG_TYPE, type)
            fragment.arguments = args  // Attach the bundle to the fragment
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hide nav and sidebar
        (activity as? MainActivity)?.hideNavigation()

        val login = arguments?.getString(ARG_LOGIN) ?: ""
        val avatarUrl = arguments?.getString(ARG_AVATAR) ?: ""
        val htmlUrl = arguments?.getString(ARG_HTML) ?: ""
        val type = arguments?.getString(ARG_TYPE) ?: ""

        binding.detailLogin.text = login
        binding.detailType.text = type

        binding.detailAvatarProgress.visibility = View.VISIBLE
        binding.detailAvatar.load(avatarUrl) {
            listener(
                onSuccess = { _, _ -> binding.detailAvatarProgress.visibility = View.GONE },
                onError = { _, _ -> binding.detailAvatarProgress.visibility = View.GONE }
            )
        }

        // Open github proflle button
        binding.openProfileButton.setOnClickListener {
            if (htmlUrl.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, htmlUrl.toUri())
                startActivity(intent)  // Launch the browser
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showNavigation()
        _binding = null
    }
}
