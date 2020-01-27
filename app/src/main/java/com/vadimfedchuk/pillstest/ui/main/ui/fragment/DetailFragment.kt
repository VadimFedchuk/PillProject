package com.vadimfedchuk.pillstest.ui.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vadimfedchuk.pillstest.R
import com.vadimfedchuk.pillstest.ui.main.db.model.Pills
import com.vadimfedchuk.pillstest.ui.main.utils.KEY_ID_FOR_DETAIL
import com.vadimfedchuk.pillstest.ui.main.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.app_bar_main.*

class DetailFragment : Fragment() {

    private lateinit var toolbar: Toolbar

    companion object {
        fun newInstance(id: Int): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_ID_FOR_DETAIL, id)
                }
            }
        }
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        viewModel.getPills(arguments!![KEY_ID_FOR_DETAIL] as Int).observe(this, Observer<Pills> { pill ->
            bindData(pill, view)
        })
        initBackButton()
        return view
    }

    private fun initBackButton() {
        toolbar = activity!!.findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
    }

    override fun onDetach() {
        toolbar.navigationIcon = null
        super.onDetach()
    }

    private fun bindData(pill: Pills, view: View) {
        view.findViewById<TextView>(R.id.trade_label_tv).text = pill.labelTrade
        view.findViewById<TextView>(R.id.manufacturer_name_tv).text = pill.nameManufacturer
        view.findViewById<TextView>(R.id.packaging_description_tv).text = pill.packagingDescription
        view.findViewById<TextView>(R.id.composition_description_tv).text = pill.compositionDescription
        view.findViewById<TextView>(R.id.composition_inn_tv).text = "${pill.innName}, ${pill.innId}"
        view.findViewById<TextView>(R.id.composition_pharm_form_tv).text = "${pill.pharmFormName}, ${pill.pharmFormId}"
    }
}