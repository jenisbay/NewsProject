package com.example.newsproject.features.saved

import android.graphics.Canvas
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.BaseApplication
import com.example.newsproject.MainActivity
import com.example.newsproject.R
import com.example.newsproject.databinding.FragmentSavedNewsBinding
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.nio.file.Files.delete
import javax.inject.Inject


class SavedNewsFragment : Fragment() {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding: FragmentSavedNewsBinding get() = _binding!!

    @Inject
    lateinit var savedNewsViewModel: SavedNewsViewModel

    @Inject
    lateinit var savedNewsAdapter: SavedNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MainActivity).application as BaseApplication).appComponent.injectSavedNewsFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNews()
        setupListeners()
    }

    private fun observeNews() {
        savedNewsViewModel.news.observe(viewLifecycleOwner) { articles ->
            savedNewsAdapter.setData(articles.toList())
        }
    }


    private fun setupListeners() {
        savedNewsAdapter.setOnClickListener { article ->

            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_webViewActivity, bundle)
        }

        binding.newsRecyclerView.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.newsRecyclerView)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val article = (viewHolder as SavedNewsAdapter.SavedNewsViewHolder).article
            article?.let { _article ->
                savedNewsViewModel.deleteArticle(_article)
                view?.let { _view ->
                    Snackbar.make(_view, "Article successfully deleted", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(ContextCompat.getColor(this@SavedNewsFragment.requireContext(), R.color.red_delete_bg))
                .addActionIcon(R.drawable.ic_delete)
                .addSwipeLeftLabel(resources.getString(R.string.delete))
                .setSwipeLeftLabelColor(resources.getColor(R.color.white))
                .setSwipeLeftActionIconTint(resources.getColor(R.color.white))
                .addCornerRadius(1, 16)
                .addSwipeLeftPadding(TypedValue@ 1, 0f, 8.0f, 0f)
                .setSwipeLeftLabelTextSize(TypedValue@ 1, 32.0f)

                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }


    }
}