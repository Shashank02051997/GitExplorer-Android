package com.shashank.platform.gitexplorer

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashank.platform.gitexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dataBind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBind.textGitCommand.text =
            Html.fromHtml(resources.getString(R.string.git_command_explorer))

    }
}
