package com.example.appfootprint.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.appfootprint.MainActivity
import com.example.appfootprint.databinding.FragmentViewArticleBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_view_article.*


class ViewArticleFragment : Fragment() {

    lateinit var viewModel: BreakingNewsViewModel
    private lateinit var mBinding: FragmentViewArticleBinding
    val args: ViewArticleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentViewArticleBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Noticia Guardada", Snackbar.LENGTH_SHORT).show()
        }
    }

}