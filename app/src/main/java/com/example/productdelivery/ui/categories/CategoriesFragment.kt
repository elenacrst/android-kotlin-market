package com.example.productdelivery.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.R
import com.example.productdelivery.data.Result
import com.example.productdelivery.data.local.LocalInformation
import com.example.productdelivery.data.model.Category
import com.example.productdelivery.databinding.FragmentCategoriesBinding
import com.example.productdelivery.ui.BaseActivity
import com.example.productdelivery.ui.BaseFragment
import com.example.productdelivery.util.Event
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CategoriesFragment : BaseFragment() {
    private lateinit var categoriesBinding: FragmentCategoriesBinding

    @Inject
    lateinit var categoriesViewModel: CategoryViewModel

    private lateinit var adapter: CategoriesAdapter
    private var categoriesLoadingObserver: Observer<Event<Result<*>>> = createLoadingObserver(
        progressListener = {
            categoriesBinding.pbLoading.visibility = View.VISIBLE
        },
        successListener = {
            categoriesBinding.pbLoading.visibility = View.GONE
            categoriesBinding.rvCategories.visibility = View.VISIBLE

        },
        errorListener = {
            categoriesBinding.pbLoading.visibility = View.GONE
        }
    )
    private var categoriesObserver: Observer<List<Category>> = createCategoriesObserver()

    private fun createCategoriesObserver(): Observer<List<Category>> {
        return Observer {
            adapter.setData(it ?: listOf())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        categoriesBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_categories, container, false)
        setupRecyclerView()

        DeliveryApplication.deliveryApplicationContext.appComponent.inject(this)

        return categoriesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesViewModel.categoriesEvent.observe(viewLifecycleOwner, categoriesLoadingObserver)
        categoriesViewModel.categories.observe(viewLifecycleOwner, categoriesObserver)
        if (categoriesViewModel.categories.value?.isNotEmpty() != true) {
            categoriesViewModel.getCategories()
        }
    }

    private fun setupRecyclerView() {
        adapter = CategoriesAdapter(createCellListener())
        categoriesBinding.rvCategories.adapter = adapter
    }

    private fun createCellListener(): CategoryCellListener {
        return CategoryCellListener { id ->
            id?.let {
                findNavController().navigate(
                    CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(
                        it
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is BaseActivity) {
            (activity as BaseActivity).supportActionBar?.title =
                getString(R.string.categories)
        }
    }
}

