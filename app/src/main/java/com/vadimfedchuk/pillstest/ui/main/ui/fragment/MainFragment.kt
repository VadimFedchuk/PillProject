package com.vadimfedchuk.pillstest.ui.main.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vadimfedchuk.pillstest.R
import com.vadimfedchuk.pillstest.ui.main.adapters.PillsAdapter
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills
import com.vadimfedchuk.pillstest.ui.main.ui.activity.Communicator
import com.vadimfedchuk.pillstest.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var adapterPills: PillsAdapter

    private lateinit var communicator: Communicator

    private lateinit var pillsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        val progress_view = view.findViewById(R.id.progress_load) as ProgressBar

        setHasOptionsMenu(true)
        communicator = activity as Communicator
        pillsRecyclerView = view.findViewById(R.id.pills_recycler_view)
        initList()
        viewModel.listPills!!.observe(this, Observer<List<Pills>> { list ->
            Log.i("TEST", "111")
            when(list.isEmpty()) {
                true -> view.findViewById<TextView>(R.id.empty_list_tv).visibility = View.VISIBLE
                false -> view.findViewById<TextView>(R.id.empty_list_tv).visibility = View.GONE
            }
            adapterPills.setData(ArrayList(list))

        })
        viewModel.isLoadData.observe(this, Observer<Boolean> { isLoad ->
            progress_view.visibility = View.GONE
            if(!isLoad) {
                view.findViewById<TextView>(R.id.empty_list_tv).visibility = View.VISIBLE
            }
        })
        val fabRefresh = view.findViewById<FloatingActionButton>(R.id.fab_refresh)
        fabRefresh.setOnClickListener {
            adapterPills.pills.clear()
            adapterPills.notifyDataSetChanged()
            progress_view.visibility = View.VISIBLE
            viewModel.refreshData()
        }

        pillsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapterPills.itemCount - 1) {
                    progress_view.visibility = View.VISIBLE
                        viewModel.loadMorePills()
                    }

                super.onScrolled(recyclerView, dx, dy)
            }})

        return view
    }

    private fun initList() {
        pillsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapterPills = PillsAdapter(arrayListOf(), onClickItem = { id ->
            communicator.openDetailFragment(id)
        })
        pillsRecyclerView.adapter = adapterPills
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(activity!!.componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (!searchView.isIconified) {
                    viewModel.getPillsByQuery(query)
                    searchView.setQuery("", false)
                    searchView.clearFocus()
                    searchView.isIconified = true
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
