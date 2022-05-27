package com.champnc.newsapp_scgtest

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.champnc.newsapp_scgtest.databinding.FragmentSecondBinding
import com.champnc.newsapp_scgtest.viewmodel.NewsViewModel


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    companion object {
        const val ARG_ARTICLE_TITLE = "arg_article_title"
        const val ARG_ARTICLE_CONTENT = "arg_article_content"
        const val ARG_ARTICLE_IMG_URL = "arg_article_img_url"
        const val ARG_ARTICLE_PUBLISH_AT = "arg_article_publish_at"
    }

    private var _binding: FragmentSecondBinding? = null
    private val model: NewsViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { data ->
            binding.tvNewsDetailTitle.text = data.getString(ARG_ARTICLE_TITLE)
            binding.tvNewsDetailContent.text = data.getString(ARG_ARTICLE_CONTENT)
            Glide.with(requireActivity()).load(data.getString(ARG_ARTICLE_IMG_URL))
                .apply(RequestOptions().override(1000, 1000).placeholder(R.drawable.ic_placeholder))
                .fitCenter().into(binding.ivNewsDetail)
            binding.tvNewsDetailDate.text = data.getString(ARG_ARTICLE_PUBLISH_AT)!!.dateFromISOtoSimpleUTC()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}