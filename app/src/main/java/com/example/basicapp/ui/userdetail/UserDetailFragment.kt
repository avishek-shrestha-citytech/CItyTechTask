package com.example.basicapp.ui.userdetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil3.load
import com.example.basicapp.databinding.FragmentUserDetailBinding
import com.example.basicapp.ui.main.MainActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.example.basicapp.data.model.GithubUser

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private var user: GithubUser? = null

    companion object {
        private const val ARG_USER = "user"

        fun newInstance(user: GithubUser): UserDetailFragment {
            val fragment = UserDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_USER, user)
            fragment.arguments = bundle
            return fragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("user", GithubUser::class.java)
    }

    //Depreciated in the API 33
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        user = arguments?.getParcelable("user")
//    }

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

        // Hide nav and sidebar
        (activity as? MainActivity)?.hideNavigation()

        // Make sure user is not null
        user?.let { githubUser ->
            binding.detailLogin.text = githubUser.login
            binding.detailType.text = githubUser.type

            binding.detailAvatarProgress.visibility = View.VISIBLE
            binding.detailAvatar.load(githubUser.avatarurl) {
                listener(
                    onSuccess = { _, _ -> binding.detailAvatarProgress.isVisible = false },
                    onError = { _, _ -> binding.detailAvatarProgress.isVisible = false }
                )
            }

            // Open github profile button
            binding.openProfileButton.setOnClickListener {
                if (githubUser.htmlurl.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, githubUser.htmlurl.toUri())
                    startActivity(intent)  // Launch the browser
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showNavigation()
        _binding = null
    }
}
