package com.shashank.platform.gitexplorer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashank.platform.gitexplorer.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        const val PRIMARY_OPTIONS = "primary_options"
        const val SECONDARY_OPTIONS = "secondary_options"
        const val LABEL = "label"
        const val VALUE = "value"
        const val USAGE = "usage"
        const val NB = "nb"
        const val FILENAME = "git_command_explorer.json"
    }

    private lateinit var dataBind: ActivityMainBinding
    private var primaryOptions = ArrayList<PrimaryOptions>()
    private var primaryOptionsValue = ""
    private var secondaryOptions = ArrayList<SecondaryOptions>()
    private lateinit var jsonFileObject: JSONObject
    private var usage = ""
    private var note = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBind.textGitCommand.text =
            Html.fromHtml(resources.getString(R.string.git_command_explorer))
        jsonFileObject = JSONObject(loadJSONFromAsset())
        getPrimaryOptions()
        dataBind.inputFirstField.setOnTouchListener { p0, p1 ->
            dataBind.inputFirstField.showDropDown()
            false
        }
        dataBind.inputSecondField.setOnTouchListener { p0, p1 ->
            dataBind.inputSecondField.showDropDown()
            false
        }
        dataBind.inputFirstField.setOnItemClickListener { adapterView, view, pos, l ->
            primaryOptionsValue = primaryOptions.find {
                it.label == dataBind.inputFirstField.text.toString()
            }?.value!!
            dismissKeyboard(dataBind.inputFirstField)
            dataBind.cardViewSecondField.visibility = View.VISIBLE
            dataBind.textNote.visibility = View.GONE
            dataBind.cardViewNote.visibility = View.GONE
            dataBind.inputSecondField.text.clear()
            dataBind.textDisplayGitCommand.text = ""
            dataBind.textDisplayNote.text = ""
            getSecondaryOptions()
        }
        dataBind.inputSecondField.setOnItemClickListener { adapterView, view, i, l ->
            dismissKeyboard(dataBind.inputFirstField)
            usage = secondaryOptions.find {
                it.label == dataBind.inputSecondField.text.toString()
            }?.usage!!
            note = secondaryOptions.find {
                it.label == dataBind.inputSecondField.text.toString()
            }?.nb!!
            dataBind.textNote.visibility = if (note == "") View.GONE else View.VISIBLE
            dataBind.cardViewNote.visibility = if (note == "") View.GONE else View.VISIBLE
            dataBind.textDisplayGitCommand.text = usage
            dataBind.textDisplayNote.text = note

        }
    }


    private fun getPrimaryOptions() {
        val jsonPrimaryOptionsArray = jsonFileObject.getJSONArray(PRIMARY_OPTIONS)
        for (i in 0 until jsonPrimaryOptionsArray.length()) {
            val jsonObject = jsonPrimaryOptionsArray.getJSONObject(i)
            val primary = PrimaryOptions()
            primary.value = jsonObject.getString(VALUE)
            primary.label = jsonObject.getString(LABEL)
            primaryOptions.add(primary)
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, primaryOptions.map {
                it.label
            }
        )
        dataBind.inputFirstField.setAdapter(adapter)
    }

    private fun getSecondaryOptions() {
        secondaryOptions.clear()
        val jsonSecondaryOptionsObject = jsonFileObject.getJSONObject(SECONDARY_OPTIONS)
        val jsonArray = jsonSecondaryOptionsObject.getJSONArray(primaryOptionsValue)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val secondary = SecondaryOptions()
            secondary.value = jsonObject.getString(VALUE)
            secondary.label = jsonObject.getString(LABEL)
            if (jsonObject.has("usage"))
                secondary.usage = jsonObject.getString(USAGE)
            if (jsonObject.has("nb"))
                secondary.nb = jsonObject.getString(NB)
            secondaryOptions.add(secondary)
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, secondaryOptions.map {
                it.label
            }
        )
        dataBind.inputSecondField.setAdapter(adapter)
    }

    private fun Context.dismissKeyboard(view: View?) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun loadJSONFromAsset(): String {
        return assets.open(FILENAME).bufferedReader().use {
            it.readText()
        }
    }
}
