package com.example.basicapp.ui.news_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManagerNonConfig
import coil3.load
import coil3.request.crossfade
import com.example.basicapp.R
import com.example.basicapp.databinding.FragmentNewsDetailBinding
import com.example.basicapp.databinding.FragmentNewsFeedBinding
import com.google.android.material.button.MaterialButton
import androidx.core.net.toUri

class NewsDetailFragment : Fragment() {

    //View Binding Initialization
    private var _binding: FragmentNewsDetailBinding?=null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE = "image"
        private const val ARG_URL = "url"
        private const val ARG_SOURCE = "source"
        private const val ARG_DATE = "date"

        fun newInstance(
            title: String,
            description: String?,
            image: String?,
            url: String,
            source: String,
            date: String
        ): NewsDetailFragment {
            return NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_IMAGE, image)
                    putString(ARG_URL, url)
                    putString(ARG_SOURCE, source)
                    putString(ARG_DATE, date)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE) ?: ""
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""
        val image = arguments?.getString(ARG_IMAGE)
        val url = arguments?.getString(ARG_URL) ?: ""
        val source = arguments?.getString(ARG_SOURCE) ?: ""
        val date = arguments?.getString(ARG_DATE) ?: ""

        binding.detailTitle.text = title
        binding.detailSource.text = source
        binding.detailDate.text = formatDate(date)
        binding.detailDescription.text = description

        if (!image.isNullOrEmpty()) {
            binding.detailImage.load(image) {
                crossfade(true)
            }
        } else {
            binding.detailImage.visibility = View.GONE
        }

        binding.readMoreButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            dateString.substringBefore("T")
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
