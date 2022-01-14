package com.example.slideandroiddevchallenge.view.dialogs

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.slideandroiddevchallenge.R
import com.example.slideandroiddevchallenge.databinding.AddUserBottomSheetDialogBinding
import com.example.slideandroiddevchallenge.utils.RegexUtil
import com.example.slideandroiddevchallenge.view.extensions.checkEmptyEditText
import com.example.slideandroiddevchallenge.view.extensions.isValid
import com.example.slideandroiddevchallenge.view.extensions.setError
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateNewUserBottomSheetDialog(private val listener: AddNewUserListener?) :
    BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CreateNewUserBottomSheetDialog"
    }

    lateinit var binding: AddUserBottomSheetDialogBinding


    override fun getTheme(): Int {
        return R.style.Theme_CustomBottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_user_bottom_sheet_dialog,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameEditText.doAfterTextChanged {
            isValidName()
        }

        binding.emailEditText.doAfterTextChanged {
            isValidEmail()
        }

        binding.submitButton.setOnClickListener {
            if (isAllValid()) {
                listener?.onSubmitNewUserClicked(name = getName(), email = getEmail())
                dismiss()
            }
        }
    }

    private fun getName() = binding.nameEditText.text?.toString().orEmpty()

    private fun getEmail() = binding.emailEditText.text?.toString().orEmpty()

    private fun isAllValid(): Boolean {
        var isValid = true

        if (!isValidName()) isValid = false
        if (!isValidEmail()) isValid = false

        return isValid
    }

    private fun isValidName(): Boolean {
        var isValid = true
        when {
            binding.nameEditText.checkEmptyEditText() -> {
                binding.nameInputLayout.setError(
                    true,
                    getString(R.string.name_is_required)
                )
                isValid = false
            }
            !binding.nameInputLayout.isValid(
                RegexUtil.name,
                getString(R.string.name_is_incorrect)
            ) -> {
                isValid = false
            }
        }
        return isValid
    }

    private fun isValidEmail(): Boolean {
        var isValid = true
        when {
            binding.emailEditText.checkEmptyEditText() -> {
                binding.nameInputLayout.setError(
                    true,
                    getString(R.string.email_is_required)
                )
                isValid = false
            }
            !binding.emailInputLayout.isValid(
                Patterns.EMAIL_ADDRESS.toRegex(),
                getString(R.string.email_is_incorrect)
            ) -> {
                isValid = false
            }
        }
        return isValid
    }

    interface AddNewUserListener {
        fun onSubmitNewUserClicked(name: String, email: String)
    }
}