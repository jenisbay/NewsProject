package com.example.newsproject.features.webview

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.navArgs
import com.example.newsproject.BaseApplication
import com.example.newsproject.R
import com.example.newsproject.data.models.Article
import com.example.newsproject.databinding.ActivityWebViewBinding
import javax.inject.Inject


class WebViewActivity : AppCompatActivity() {

    private var _binding: ActivityWebViewBinding? = null
    private val binding: ActivityWebViewBinding get() = _binding!!
    private val args: WebViewActivityArgs by navArgs()
    private lateinit var article: Article

    @Inject
    lateinit var webViewViewModel: WebViewViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inject()

        val webView = binding.webView
        article = args.article
        val url = article.url
        webView.webViewClient = WebClient()
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    inner class WebClient : WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBarWebView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_view_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.share -> {
            val messageBody = formatShareMessageBody(article)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, messageBody)
            shareIntent.type = "text/plain"
            val chooserIntent = Intent.createChooser(shareIntent, "Share Options")
            startActivity(chooserIntent)
            true
        }
        R.id.save -> {
            webViewViewModel.saveArticle(article)
            Toast.makeText(this, "Article successfully saved", Toast.LENGTH_SHORT).show()
            item.setIcon(R.drawable.ic_filled_bookmark)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun formatShareMessageBody(article: Article): String {
        return "${article.title}\n\n${article.url}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun inject(){
        (application as BaseApplication).appComponent.injectWebViewActivity(this)
    }

}