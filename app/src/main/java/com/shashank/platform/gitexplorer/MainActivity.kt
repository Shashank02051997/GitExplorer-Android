package com.shashank.platform.gitexplorer

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashank.platform.gitexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dataBind: ActivityMainBinding
    private var firstField = mutableListOf<String>()
    private var secondField = mutableListOf<String>()
    private var usage = ""
    private var note = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBind.textGitCommand.text =
            Html.fromHtml(resources.getString(R.string.git_command_explorer))
        addFirstFieldData()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, firstField
        )
        dataBind.inputFirstField.setAdapter(adapter)
        dataBind.inputFirstField.setOnTouchListener { p0, p1 ->
            dataBind.inputFirstField.showDropDown()
            false
        }
        dataBind.inputSecondField.setOnTouchListener { p0, p1 ->
            dataBind.inputSecondField.showDropDown()
            false
        }
        dataBind.inputFirstField.setOnItemClickListener { adapterView, view, pos, l ->
            dismissKeyboard(dataBind.inputFirstField)
            dataBind.cardViewSecondField.visibility = View.VISIBLE
            dataBind.textNote.visibility = View.GONE
            dataBind.cardViewNote.visibility = View.GONE
            dataBind.inputSecondField.text.clear()
            dataBind.textDisplayGitCommand.text = ""
            dataBind.textDisplayNote.text = ""
            addSecondFieldData()
        }
        dataBind.inputSecondField.setOnItemClickListener { adapterView, view, i, l ->
            dismissKeyboard(dataBind.inputFirstField)
            dataBind.textNote.visibility = View.VISIBLE
            dataBind.cardViewNote.visibility = View.VISIBLE
            when (dataBind.inputFirstField.text.toString()) {
                firstField[0] -> {
                    when (dataBind.inputSecondField.text.toString()) {
                        secondField[0] -> {
                            usage = "git add <file.ext>"
                            note =
                                "To add all the files in the current directory, use \"git add .\"\n To add a directory use \"git add <directory>\""
                        }
                        secondField[1] -> {
                            usage = ""
                            note = ""
                        }
                        secondField[2] -> {
                            usage = ""
                            note = ""
                        }
                    }
                }
                firstField[1] -> {
                    when (dataBind.inputSecondField.text.toString()) {
                        secondField[0] -> {
                            usage = ""
                            note = ""
                        }
                        secondField[1] -> {
                            usage = ""
                            note = ""
                        }
                        secondField[2] -> {
                            usage = ""
                            note = ""
                        }
                    }
                }

            }
            dataBind.textDisplayGitCommand.text = usage
            dataBind.textDisplayNote.text = note

        }
    }

    private fun addFirstFieldData() {
        firstField.add("add")
        firstField.add("clone")
        firstField.add("commit")
        firstField.add("compare two commits")
    }

    private fun addSecondFieldData() {
        secondField.clear()
        when (dataBind.inputFirstField.text.toString()) {
            firstField[0] -> {
                secondField.add("new changes")
                secondField.add("a new branch")
                secondField.add("new remote repo")
            }
            firstField[1] -> {
                secondField.add("existing repo into a new directory")
                secondField.add("existing repo into the current directory")
                secondField.add("existing repo along with submodules into the current directory")
            }
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, secondField
        )
        dataBind.inputSecondField.setAdapter(adapter)
    }

    fun Context.dismissKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
