package com.example.appfootprint

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appfootprint.databinding.ActivityMainBinding
import com.example.appfootprint.db.ArticleDatabase
import com.example.appfootprint.db.RecollectDatabase
import com.example.appfootprint.repository.NewsRepository
import com.example.appfootprint.repository.RecollectRepository
import com.example.appfootprint.repository.ResultRepository
import com.example.appfootprint.ui.footprint.calfootprint.ResultViewModel
import com.example.appfootprint.ui.footprint.calfootprint.ViewModelProviderFactory
import com.example.appfootprint.ui.news.BreakingNewsViewModel
import com.example.appfootprint.ui.news.BreakingNewsViewModelProviderFactory
import com.example.appfootprint.ui.recollect.AddRecollectViewModel
import com.example.appfootprint.ui.recollect.RecollectViewModelProviderFactory
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    private var mFirebaseAuth: FirebaseAuth? = null
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var viewModel2: AddRecollectViewModel
    lateinit var viewModel3: ResultViewModel

    private val authResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                Toast.makeText(this, "Bienvenido...", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(2000)
        setTheme(R.style.Theme_AppFootPrint)

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

        val headerView : View = navView.getHeaderView(0)

        headerView.nameUser.text = FirebaseAuth.getInstance().currentUser?.displayName

        headerView.emailUser.text = FirebaseAuth.getInstance().currentUser?.email

        Glide.with(this)
            .load(FirebaseAuth.getInstance().currentUser?.photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(headerView.imageView)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_recollect, R.id.nav_footprint, R.id.nav_news, R.id.nav_savednews, R.id.nav_profile), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

       setupAuth()

    }

    private fun setupAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser
            if (user == null) {
                authResult.launch(
                    AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                            Arrays.asList(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build())
                        )
                        .build()
                )
            }
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

    override fun onResume() {
        super.onResume()
        mFirebaseAuth?.addAuthStateListener (mAuthListener)
    }

    override fun onPause() {
        super.onPause()
        mFirebaseAuth?.removeAuthStateListener (mAuthListener)
    }


}