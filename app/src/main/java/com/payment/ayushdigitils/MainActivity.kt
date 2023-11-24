package com.payment.ayushdigitils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.payment.ayushdigitils.databinding.ActivityMainBinding
import com.payment.ayushdigitils.ex.setupWithNavController
import com.payment.ayushdigitils.ui.base.BaseDialogFragment
import com.payment.ayushdigitils.ui.base.BaseFragment

class MainActivity : AppCompatActivity(),
    BaseFragment.ShowProgressBar ,
    BaseDialogFragment.ShowProgressBar {
    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.btmNav


        val navGraphIds = listOf(
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_category,
            R.navigation.nav_graph_profile
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this) { navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment -> showBottomNav()
                    R.id.reportFragment -> showBottomNav()
                    R.id.profileFragment -> showBottomNav()
                    else -> hideBottomNav()
                }


            }


        }
        currentNavController = controller

    }

    override fun onNavigateUp(): Boolean {
        val dest = currentNavController?.value?.currentDestination

        dest?.let {
            return when (it.id) {
                R.id.successFragment -> false
                else -> true
            }
        } ?: return false
    }

    private fun showBottomNav() {
        binding.btmNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.btmNav.visibility = View.GONE
    }

    override fun setVisibility(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    override fun onSupportNavigateUp(): Boolean {
        val dest = currentNavController?.value?.currentDestination

        dest?.let {
            return when (it.id) {
                R.id.paySprintDmtInvoiceFragment -> {

                    false
                }
                R.id.profileFragment -> true
                else -> true
            }
        } ?: return false
        //return currentNavController?.value?.navigateUp() ?: false
    }


}