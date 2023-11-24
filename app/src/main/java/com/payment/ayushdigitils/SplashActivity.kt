package com.payment.ayushdigitils

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.payment.ayushdigitils.databinding.ActivitySplashBinding
import com.payment.ayushdigitils.ui.base.BaseActivity
import com.payment.ayushdigitils.ui.signin.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(
             ColorDrawable(Color.parseColor("#AA3939"))
        );
        lifecycleScope.launch(Dispatchers.IO) {
            delay(200)
            if (prefs.getIsAnonymous()) {
                launchLogin()
            }else{
               launchMain()
            }

        }

    }

    private fun launchLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun launchMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}