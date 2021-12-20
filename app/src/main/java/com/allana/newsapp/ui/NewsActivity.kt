package com.allana.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.allana.newsapp.R
import com.allana.newsapp.databinding.ActivityNewsBinding
import com.allana.newsapp.db.ArticleDatabase
import com.allana.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Setting bottom navigation with navigation controller and set the appbar(toolbar) label
         */
        val navController = findNavController(R.id.newsNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = setOf(
                R.id.breakingNewsFragment,
                R.id.savedNewsFragment,
                R.id.searchNewsFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.newsNavHostFragment)
        return navController.navigateUp()
    }

    fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }
}