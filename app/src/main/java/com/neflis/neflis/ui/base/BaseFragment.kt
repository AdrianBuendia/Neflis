package com.neflis.neflis.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.neflis.neflis.R
import com.neflis.neflis.core.hideKeyboard
import com.neflis.neflis.core.util.Resource

abstract class BaseFragment : Fragment() {

    private var snacky: Snackbar? = null

    fun showError(message: String) {
        this.view?.let {
            hideKeyboard()
            Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    fun showSuccess(message: String) {
        this.view?.let {
            hideKeyboard()
            Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_LONG
            ).show()
        }

    }

    fun getErrorMessageFromResource(resource: Resource<Any>): String {
        return resource.msg ?: getString(R.string.error_request)
    }


    @Throws(IllegalArgumentException::class)
    fun safeNav(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.hideKeyboard()
    }
}