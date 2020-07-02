package com.example.productdelivery.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.R
import com.example.productdelivery.data.Result
import com.example.productdelivery.databinding.FragmentProductsBinding
import com.example.productdelivery.ui.BaseActivity
import com.example.productdelivery.ui.BaseFragment
import com.example.productdelivery.util.Event
import javax.inject.Inject

class ProductsFragment : BaseFragment() {

    private lateinit var productsBinding: FragmentProductsBinding

    private lateinit var adapter: ProductsAdapter
    private var categoryId: Long = 0

    @Inject
    lateinit var productsViewModel: ProductViewModel

    private var productsLoadingObserver: Observer<Event<Result<*>>> = createLoadingObserver(
        progressListener = {
            productsBinding.pbLoading.visibility = View.VISIBLE
        },
        successListener = {
            productsBinding.pbLoading.visibility = View.GONE
            productsBinding.rvProducts.visibility = View.VISIBLE
            adapter.setData(productsViewModel.products.value ?: listOf())
        },
        errorListener = {
            productsBinding.pbLoading.visibility = View.GONE

        }
    )

    override fun onResume() {
        super.onResume()
        if (activity is BaseActivity) {
            (activity as BaseActivity).supportActionBar?.title =
                getString(R.string.products)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productsBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_products, container, false)
        setupRecyclerView()

        DeliveryApplication.deliveryApplicationContext.appComponent.inject(this)
        val args =
            ProductsFragmentArgs.fromBundle(
                requireArguments()
            )
        categoryId = args.categoryId

        return productsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsViewModel.productsEvent.observe(viewLifecycleOwner, productsLoadingObserver)
        productsViewModel.getProductsByCategory(categoryId)
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter()
        productsBinding.rvProducts.adapter = adapter
    }
}

