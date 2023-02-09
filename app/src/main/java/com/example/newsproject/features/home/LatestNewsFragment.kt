package com.example.newsproject.features.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.BaseApplication
import com.example.newsproject.MainActivity
import com.example.newsproject.R
import com.example.newsproject.databinding.FragmentLatestNewsBinding
import com.example.newsproject.utils.Constants.LANGUAGE
import com.example.newsproject.utils.Language
import com.facebook.shimmer.ShimmerFrameLayout
import javax.inject.Inject


class LatestNewsFragment : Fragment() {

    @Inject
    lateinit var latestNewsAdapter: LatestNewsAdapter

    @Inject
    lateinit var latestNewsViewModel: LatestNewsViewModel

    private var isRefreshing: Boolean = false

    private var _binding: FragmentLatestNewsBinding? = null
    private val binding: FragmentLatestNewsBinding get() = _binding!!

    private var _shimmerFrameLayout: ShimmerFrameLayout? = null
    private val shimmerFrameLayout: ShimmerFrameLayout get() = _shimmerFrameLayout!!

    private var _recyclerView: RecyclerView? = null
    private val recyclerView: RecyclerView get() = _recyclerView!!


    private var country: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject() // Inject this fragment into AppComponent
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLatestNewsBinding.inflate(inflater, container, false)
        _shimmerFrameLayout = binding.shimmerViewContainer
        _recyclerView = binding.newsRecyclerView


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        loadNews()
    }

    private fun loadNews() {

        latestNewsViewModel.latestNews.observe(viewLifecycleOwner) {

            latestNewsAdapter.setData(it)

            if (it.isNotEmpty() || isRefreshing) {
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                isRefreshing = false
            }
        }

        latestNewsViewModel.category.observe(viewLifecycleOwner) {
            isRefreshing = true
            recyclerView.visibility = View.GONE
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()
            val language = PreferenceManager
                .getDefaultSharedPreferences(requireActivity())
                .getString(LANGUAGE, "en").toString()
            lifecycleScope.launchWhenStarted {
                latestNewsViewModel.getLatestNewsByCategory(it, language)
            }
        }
    }

    private fun setupRecyclerView() {
        latestNewsAdapter.setOnClickListener { url ->
            val bundle = Bundle().apply {
                putString("url", url)
            }
            findNavController().navigate(R.id.action_latestNewsFragment_to_webViewActivity, bundle)
        }

        binding.newsRecyclerView.apply {
            adapter = latestNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
            group.check(group.checkedChipId)
            var category = ""
            when (group.checkedChipId) {
                R.id.chipGeneral -> {
                    category = "general"
                }
                R.id.chipBusiness -> {
                    category = "business"
                }
                R.id.chipScience -> {
                    category = "science"
                }
                R.id.chipSports -> {
                    category = "sports"
                }
                R.id.chipHealth -> {
                    category = "health"
                }
                R.id.chipTechnology -> {
                    category = "technology"
                }
                R.id.chipEntertainment -> {
                    category = "entertainment"
                }
            }
            latestNewsViewModel.setCategory(category)
        }
    }

    private fun inject() {
        ((activity as MainActivity).application as BaseApplication).appComponent.injectLatestNewsFragment(
            this
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _shimmerFrameLayout = null
        _recyclerView = null
    }
}