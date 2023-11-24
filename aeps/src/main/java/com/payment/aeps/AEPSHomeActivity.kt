package com.payment.aeps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.payment.aeps.base.AepsBaseFragment
import com.payment.aeps.databinding.ActivityAepshomeBinding
import com.payment.aeps.preferences.AepsPrefRepository

class AEPSHomeActivity : AppCompatActivity(),AepsBaseFragment.ShowProgressBar {

    private lateinit var binding : ActivityAepshomeBinding
    private lateinit var prefRepository :AepsPrefRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAepshomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefRepository = AepsPrefRepository(this)

        val user_id = intent.getStringExtra("userId")
        val app_token = intent.getStringExtra("appToken")
        val aeps_type = intent.getStringExtra("aepsType")

        Log.v("intentData","==$user_id===$app_token")

        user_id?.let { prefRepository.setUserId(it) }
        app_token?.let { prefRepository.setAppToken(it) }
        aeps_type?.let { prefRepository.setAepsType(it) }
    }

    override fun setVisibility(visibility: Int) {
        binding.progressBar.visibility = visibility

    }

}