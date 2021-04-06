package com.picpay.desafio.android.users.ui.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.users.model.User
import com.picpay.desafio.android.users.ui.adapter.UserListAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val adapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupError()
    }

    private fun initViews() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar.visibilityVisible()
    }

    private fun showUserList(users: List<User>) {
        progressBar.visibilityGone()
        adapter.users = users
    }

    private fun setupError() {
        hideViews()
        showErrorToast(getString(R.string.error))
    }

    private fun showErrorToast(message: String)  {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun hideViews() {
        progressBar.visibilityGone()
        recyclerView.visibilityGone()
    }

    private fun View.visibilityGone() = run { this.visibility = GONE }
    private fun View.visibilityVisible() = run { this.visibility = VISIBLE }
}
