package com.example.productdelivery.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.productdelivery.R
import com.example.productdelivery.databinding.DialogLanguageBinding

class LanguageDialog(
    private val oldLanguage: String
) : DialogFragment() {

    private lateinit var binding: DialogLanguageBinding
    private var callback: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_language, container, false)

        dialog?.let {
            dialog!!.window?.let {
                dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            isCancelable = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btOk.setOnClickListener {
            dismiss()

            callback?.let {
                val selectedId = binding.rg.checkedRadioButtonId
                val selectedTag = (view.findViewById(selectedId) as RadioButton).tag.toString()

                callback?.invoke(selectedTag)
            }
        }

        binding.btCancel.setOnClickListener {
            dismiss()
        }

        setInitialSelection()
    }

    fun setPositiveAction(callback: ((String) -> Unit)): LanguageDialog {
        this.callback = callback
        return this
    }

    private fun setInitialSelection() {
        when (oldLanguage) {
            LANGUAGE_EN -> binding.rbEn.isChecked = true
            LANGUAGE_RO -> binding.rbRo.isChecked = true
        }
    }

    companion object {

        fun createInstance(oldLanguage: String): LanguageDialog {
            val dialog = LanguageDialog(
                oldLanguage
            )
            dialog.setStyle(STYLE_NO_TITLE, 0)
            return dialog
        }
    }
}

const val LANGUAGE_RO = "ro"
const val LANGUAGE_EN = "en"