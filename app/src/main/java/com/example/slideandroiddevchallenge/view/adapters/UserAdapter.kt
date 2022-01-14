package com.example.slideandroiddevchallenge.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.slideandroiddevchallenge.R
import com.example.slideandroiddevchallenge.databinding.UserRowItemBinding
import com.example.slideandroiddevchallenge.entity.response.User

class UserAdapter(
    private val users: ArrayList<User>,
    private val listener: UsersClickListener?
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(user = users[position])
    }

    override fun getItemCount() = users.size

    class ViewHolder(
        private val binding: UserRowItemBinding, private val listener: UsersClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.name = user.name.orEmpty()
            binding.email = user.email.orEmpty()
            binding.gender = user.gender.orEmpty()

            binding.container.setOnLongClickListener {

                listener?.onDeleteUser(user)
                return@setOnLongClickListener true
            }
        }
    }

    interface UsersClickListener {
        fun onDeleteUser(user: User)
    }
}