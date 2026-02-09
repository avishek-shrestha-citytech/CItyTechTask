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
import coil3.load
import coil3.request.crossfade
import com.example.basicapp.R
import com.google.android.material.button.MaterialButton

class NewsDetailFragment : Fragment() {

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
    ): View? {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE) ?: ""
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""
        val image = arguments?.getString(ARG_IMAGE)
        val url = arguments?.getString(ARG_URL) ?: ""
        val source = arguments?.getString(ARG_SOURCE) ?: ""
        val date = arguments?.getString(ARG_DATE) ?: ""

        val detailImage: ImageView = view.findViewById(R.id.detailImage)
        val detailTitle: TextView = view.findViewById(R.id.detailTitle)
        val detailSource: TextView = view.findViewById(R.id.detailSource)
        val detailDate: TextView = view.findViewById(R.id.detailDate)
        val detailDescription: TextView = view.findViewById(R.id.detailDescription)
        val readMoreButton: MaterialButton = view.findViewById(R.id.readMoreButton)

        detailTitle.text = title
        detailSource.text = source
        detailDate.text = formatDate(date)
        detailDescription.text = description

        if (!image.isNullOrEmpty()) {
            detailImage.load(image) {
                crossfade(true)
            }
        } else {
            detailImage.visibility = View.GONE
        }

        readMoreButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun formatDate(dateString: String): String {
        return try {
            // Simple formatting - just show the date part
            dateString.substringBefore("T")
        } catch (e: Exception) {
            dateString
        }
    }
}
