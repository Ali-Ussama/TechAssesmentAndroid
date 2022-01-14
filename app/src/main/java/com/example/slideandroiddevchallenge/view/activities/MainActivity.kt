package com.example.slideandroiddevchallenge.view.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.slideandroiddevchallenge.R
import com.example.slideandroiddevchallenge.databinding.ActivityMainBinding
import com.example.slideandroiddevchallenge.entity.response.User
import com.example.slideandroiddevchallenge.network.networkCall.ServerCallBack
import com.example.slideandroiddevchallenge.utils.Constants
import com.example.slideandroiddevchallenge.view.adapters.UserAdapter
import com.example.slideandroiddevchallenge.view.dialogs.CreateNewUserBottomSheetDialog
import com.example.slideandroiddevchallenge.view.extensions.setTransparentStatusWithBlackIcons
import com.example.slideandroiddevchallenge.viewModels.MainViewModel

class MainActivity : BaseActivity(), CreateNewUserBottomSheetDialog.AddNewUserListener,
    UserAdapter.UsersClickListener {

    lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setTransparentStatusWithBlackIcons()

        getUsersPagesCount()

        binding.swipRefresh.setOnRefreshListener {
            getUsersPagesCount()
        }
    }

    private fun getUsersPagesCount() {
        viewModel.getUsersMeta().observe(this, { response ->
            when (response.status) {
                ServerCallBack.Status.LOADING -> {
                    showLoading()
                }
                ServerCallBack.Status.SUCCESS -> {
                    hideLoading()
                    response.data?.meta?.let { meta ->
                        meta.pagination?.pages?.let { pages ->
                            meta.pagination.page?.let { page ->
                                if (page < pages) {
                                    getUsersLastPage(pages)
                                } else {
                                    response.data.data?.let { users ->
                                        if (users.isNotEmpty()) {
                                            displayUsers(users)
                                        } else {
                                            displayErrorMessage()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                ServerCallBack.Status.ERROR -> {
                    hideLoading()
                    displayErrorMessage()
                    showErrorMessage(response.message)
                }
            }
        })
    }

    private fun getUsersLastPage(page: Int?) {
        viewModel.getUsersByPage(page).observe(this, { response ->
            when (response.status) {
                ServerCallBack.Status.LOADING -> {
                    showLoading()
                }
                ServerCallBack.Status.SUCCESS -> {
                    hideLoading()
                    binding.hasUsers = response.data?.data.isNullOrEmpty().not()
                    response.data?.data?.let { users ->
                        if (users.isNotEmpty()) {
                            displayUsers(users)
                        } else {
                            displayErrorMessage()
                        }
                    }
                }
                ServerCallBack.Status.ERROR -> {
                    hideLoading()
                    displayErrorMessage()
                    showErrorMessage(response.message)
                }
            }
        })
    }

    private fun showLoading() {
        binding.swipRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipRefresh.isRefreshing = false
    }

    private fun displayErrorMessage() {
        binding.hasUsers = false
    }

    private fun hideErrorMessage() {
        binding.hasUsers = true
    }

    private fun displayUsers(users: ArrayList<User>) {
        if (users.isNotEmpty()) {
            binding.userRecyclerView.adapter = UserAdapter(users, this)
        }
    }

    private fun showErrorMessage(message: String?) {

        binding.message.text = message.orEmpty()
    }

    fun showAddNewUserDialog(view: View) {
        val dialog = CreateNewUserBottomSheetDialog(this)
        if (supportFragmentManager.findFragmentByTag(CreateNewUserBottomSheetDialog.TAG) == null) {
            dialog.show(supportFragmentManager, CreateNewUserBottomSheetDialog.TAG)
        }
    }

    override fun onSubmitNewUserClicked(name: String, email: String) {
        viewModel.createNewUser(name, email).observe(this, { response ->
            when (response.status) {
                ServerCallBack.Status.LOADING -> {
                    showLoading()
                }
                ServerCallBack.Status.SUCCESS -> {
                    hideLoading()
                    getUsersPagesCount()
                }
                ServerCallBack.Status.ERROR -> {
                    hideLoading()
                    displayErrorMessage()
                    showErrorMessage(response.message)
                }
            }
        })
    }

    override fun onDeleteUser(user: User) {
        AlertDialog.Builder(this).setMessage(
            getString(R.string.are_you_sure_message)
        ).setPositiveButton("Ok") { dialog, _ ->
            deleteUser(user)
            dialog?.dismiss()
        }.setNegativeButton("Cancel") { dialog, _ ->
            dialog?.dismiss()
        }.show()
    }

    private fun deleteUser(user: User) {
        viewModel.deleteUser(userId = user.id).observe(this, { response ->
            when (response.status) {
                ServerCallBack.Status.LOADING -> {
                    showLoading()
                }
                else -> {
                    hideLoading()
                    getUsersPagesCount()
                }
            }
        })
    }
}