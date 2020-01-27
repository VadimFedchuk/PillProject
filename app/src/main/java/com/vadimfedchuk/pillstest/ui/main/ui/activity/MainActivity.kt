package com.vadimfedchuk.pillstest.ui.main.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.vadimfedchuk.pillstest.R
import com.vadimfedchuk.pillstest.ui.main.ui.fragment.DetailFragment
import com.vadimfedchuk.pillstest.ui.main.ui.fragment.MainFragment

class MainActivity : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initFragment()
initToolbar()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Pills Module"
        setSupportActionBar(toolbar)
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

    override fun openDetailFragment(id: Int) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentDetail = DetailFragment.newInstance(id)
        transaction.apply {
            setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            replace(R.id.container, fragmentDetail)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
    }
}

interface Communicator {
    fun openDetailFragment(id: Int)
}
