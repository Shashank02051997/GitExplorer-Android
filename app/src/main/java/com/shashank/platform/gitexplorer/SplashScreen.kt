package com.shashank.platform.gitexplorer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashank.platform.gitexplorer.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    companion object {
        private const val DELAY_TIME_IN_MILLS = 2500L
    }

    private lateinit var dataBind: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        dataBind.textGitCommand.text =
            Html.fromHtml(resources.getString(R.string.git_command_explorer))
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME_IN_MILLS)
    }
}
