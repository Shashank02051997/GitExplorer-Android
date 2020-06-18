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
            when (dataBind.inputFirstField.text.toString()) {
                firstField[0] -> {
                    when (dataBind.inputSecondField.text.toString()) {
                        secondField[0] -> {
                            usage = "git add <file.ext>"
                            note =
                                "To add all the files in the current directory, use \"git add .\"\n To add a directory use \"git add <directory>\""
                        }
                        secondField[1] -> {
                            usage = "git remote add <shortname> <url>"
                            note = ""
                        }
                        secondField[2] -> {
                            usage = "git config --global alias.<alias> <command>"
                            note =
                                "e.g. git config --global alias.st status. Typing git st in the terminal now does the same thing as git status"
                        }
                        secondField[3] -> {
                            usage = "git tag -a v1.4 -m \"my version 1.4\"\ngit push --tags"
                            note = ""
                        }
                        secondField[3] -> {
                            usage =
                                "git tag -a v1.2 -m 'version 1.2' <commit-hash>\ngit push --tags"
                            note = ""
                        }
                    }
                }
                firstField[1] -> {
                    when (dataBind.inputSecondField.text.toString()) {
                        secondField[0] -> {
                            usage = "git clone <repo-url> <directory>"
                            note =
                                "The repo is cloned into the specified directory\nReplace \"directory\" with the directory you want"
                        }
                        secondField[1] -> {
                            usage = "git clone <repo-url> ."
                            note = "The repo is cloned into the current directory\nThe current directory is represented with a \".\" (period)"
                        }
                        secondField[2] -> {
                            usage = "git clone --recurse-submodules <repo-url> ."
                            note = "If git version is under 2.13, use --recursive option instead."
                        }
                        secondField[3] -> {
                            usage = "git submodule update --init --recursive\n"
                            note = ""
                        }
                    }
                }
                /*firstField[2] -> {
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
                }*/
                /*firstField[3] -> {
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
                }*/
                /*firstField[4] -> {
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
                }*/

            }
            dataBind.textNote.visibility = if (note == "") View.GONE else View.VISIBLE
            dataBind.cardViewNote.visibility = if (note == "") View.GONE else View.VISIBLE
            dataBind.textDisplayGitCommand.text = usage
            dataBind.textDisplayNote.text = note

        }
    }

    private fun addFirstFieldData() {
        firstField.add("add")
        firstField.add("clone")
    }

    private fun addSecondFieldData() {
        secondField.clear()
        when (dataBind.inputFirstField.text.toString()) {
            firstField[0] -> {
                secondField.add("new changes")
                secondField.add("new remote repo")
                secondField.add("alias")
                secondField.add("annotated tag")
                secondField.add("annotated tag for old commit")
            }
            firstField[1] -> {
                secondField.add("existing repo into a new directory")
                secondField.add("existing repo into the current directory")
                secondField.add("existing repo along with submodules into the current directory")
                secondField.add("submodules after cloning existing repo")
            }
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, secondField
        )
        dataBind.inputSecondField.setAdapter(adapter)
    }

    private fun Context.dismissKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
