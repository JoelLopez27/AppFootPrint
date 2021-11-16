package com.example.appfootprint

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appfootprint.adapters.RecollectAdapter
import com.example.appfootprint.databinding.ActivityMainBinding
import com.example.appfootprint.db.ArticleDatabase
import com.example.appfootprint.db.RecollectDatabase
import com.example.appfootprint.repository.NewsRepository
import com.example.appfootprint.repository.RecollectRepository
import com.example.appfootprint.repository.ResultRepository
import com.example.appfootprint.ui.footprint.calfootprint.ResultViewModel
import com.example.appfootprint.ui.footprint.calfootprint.ViewModelProviderFactory
import com.example.appfootprint.ui.home.HomeFragment
import com.example.appfootprint.ui.news.BreakingNewsViewModel
import com.example.appfootprint.ui.news.BreakingNewsViewModelProviderFactory
import com.example.appfootprint.ui.recollect.AddRecollectViewModel
import com.example.appfootprint.ui.recollect.RecollectFragment
import com.example.appfootprint.ui.recollect.RecollectViewModel
import com.example.appfootprint.ui.recollect.RecollectViewModelProviderFactory


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mBinding: ActivityMainBinding
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var viewModel2: AddRecollectViewModel
    lateinit var viewModel3: ResultViewModel
    lateinit var recollectAdapter: RecollectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val resultRepository = ResultRepository()
        val resultViewModelProviderFactory = ViewModelProviderFactory(resultRepository)
        viewModel3 = ViewModelProvider(this, resultViewModelProviderFactory).get(ResultViewModel::class.java)

        val recollectRepository = RecollectRepository(RecollectDatabase(this))
        val recollectProviderFactory = RecollectViewModelProviderFactory(recollectRepository)
        viewModel2 = ViewModelProvider(this, recollectProviderFactory).get(AddRecollectViewModel::class.java)


        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = BreakingNewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(BreakingNewsViewModel::class.java)

        setSupportActionBar(mBinding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        val navView: NavigationView = mBinding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_recollect, R.id.nav_footprint, R.id.nav_news, R.id.nav_savednews), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


            mBinding.appBarMain.fab.setOnClickListener {
            LaunchRecollectFragment()
        }
    }

    private fun LaunchRecollectFragment() {
        val recollectFragment = RecollectFragment()
        val fragment : Fragment? =

        supportFragmentManager.findFragmentByTag(RecollectFragment::class.java.simpleName)

        if(fragment !is RecollectFragment){
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment_content_main, recollectFragment, RecollectFragment::class.java.simpleName)
                .commit()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}