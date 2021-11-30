package com.example.appfootprint.ui.savednews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfootprint.MainActivity
import com.example.appfootprint.R
import com.example.appfootprint.adapters.NewsAdapter
import com.example.appfootprint.databinding.FragmentSavedNewsBinding
import com.example.appfootprint.ui.news.BreakingNewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*

class savedNewsFragment : Fragment() {

    private lateinit var mBinding: FragmentSavedNewsBinding
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var newsAdapter: NewsAdapter
    var totalRows : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        mBinding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        viewModel.getSizeRows().observe(viewLifecycleOwner, Observer {
            totalRows = it

            if(totalRows == 0){
                mBinding.emptyViewText.visibility = View.VISIBLE
            }else{
                mBinding.emptyViewText.visibility = View.GONE
            }
        })


        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_saved_News_to_viewArticleFragment,
                bundle
            )
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->

            newsAdapter.differ.submitList(articles)

        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                    return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Noticia Eliminada", Snackbar.LENGTH_SHORT).apply {
                    setAction("Deshacer"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvsavedNews)
        }
     }

        private fun setupRecyclerView(){
            newsAdapter = NewsAdapter()
            rvsavedNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }