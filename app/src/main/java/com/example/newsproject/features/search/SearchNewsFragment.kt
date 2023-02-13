package com.example.newsproject.features.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.BaseApplication
import com.example.newsproject.MainActivity
import com.example.newsproject.R
import com.example.newsproject.databinding.FragmentSearchNewsBinding
import com.example.newsproject.databinding.SearchNewsItemBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import javax.inject.Inject


class SearchNewsFragment : Fragment() {

    @Inject
    lateinit var searchNewsViewModel: SearchNewsViewModel
    
    @Inject
    lateinit var searchNewsAdapter: SearchNewsAdapter
    
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding: FragmentSearchNewsBinding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeNews()
        setupListeners()
    }

    private fun setupListeners(){
        binding.btnSearch.setOnClickListener {
            val query = binding.tvQuery.text.toString()
            if (query.isNotEmpty()){
                getNewsByQuery(query)
            } else {
                Toast.makeText(requireContext(), "Please input a query string!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNewsByQuery(query: String){
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launchWhenStarted {
            searchNewsViewModel.getNewsByQuery(query)
        }
    }

    private fun observeNews(){
        searchNewsViewModel.news.observe(viewLifecycleOwner){
            searchNewsAdapter.setData(it.toList())
            if (it.isNotEmpty()){
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView(){

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            MaterialDividerItemDecoration.VERTICAL
        )

        searchNewsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_webViewActivity, bundle)
        }

        binding.searchNewsRecyclerView.apply { 
            adapter = searchNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }
    
    private fun inject() {
        ((activity as MainActivity).application as BaseApplication).appComponent.injectSearchNewsFragment(
            this
        )
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}