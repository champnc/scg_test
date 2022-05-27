package com.champnc.newsapp_scgtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.champnc.newsapp_scgtest.SecondFragment.Companion.ARG_ARTICLE_CONTENT
import com.champnc.newsapp_scgtest.SecondFragment.Companion.ARG_ARTICLE_IMG_URL
import com.champnc.newsapp_scgtest.SecondFragment.Companion.ARG_ARTICLE_PUBLISH_AT
import com.champnc.newsapp_scgtest.SecondFragment.Companion.ARG_ARTICLE_TITLE
import com.champnc.newsapp_scgtest.databinding.FragmentFirstBinding
import com.champnc.newsapp_scgtest.viewmodel.NewsViewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val model: NewsViewModel by activityViewModels()
    private lateinit var adapter: NewsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter(emptyList()) {
            val bundle = bundleOf(ARG_ARTICLE_TITLE to it.title,
                ARG_ARTICLE_CONTENT to it.content,
                ARG_ARTICLE_IMG_URL to it.urlToImage,
                ARG_ARTICLE_PUBLISH_AT to it.publishedAt)
            view.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.addItemDecoration(
            DividerItemDecoration(
                binding.rvNews.context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvNews.adapter = adapter

        binding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 5 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached) {
                    model.searchNext(binding.svNews.query.toString())
                }
            }
        })
        
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svNews.clearFocus()
                model.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        model.users.observe(requireActivity()) { response ->
            response.articles?.let { it -> adapter.updateList(it) }
        }

        model.isLoading.observe(requireActivity()) {
            if (it) {
                binding.pg.visibility = View.VISIBLE
            } else {
                binding.pg.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}