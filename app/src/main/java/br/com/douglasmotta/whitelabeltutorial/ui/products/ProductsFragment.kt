package br.com.douglasmotta.whitelabeltutorial.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.douglasmotta.whitelabeltutorial.R
import br.com.douglasmotta.whitelabeltutorial.databinding.FragmentProductsBinding
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import br.com.douglasmotta.whitelabeltutorial.util.PRODUCT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()

    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initListeners()
        observeNavBackStack()
        observe()

        viewModel.getProducts()
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.apply {
            productsAdapter = ProductsAdapter()
            adapter = productsAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
        }
        productsAdapter.setOnItemClickListener {

        }
    }

    private fun initListeners() = binding.run {
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_addProductFragment)
        }
        swipeProducts.setOnRefreshListener {
            viewModel.getProducts()
            swipeProducts.isRefreshing = false
        }
    }

    private fun observeNavBackStack() {
        findNavController().run {
            val navBackStarEntry = getBackStackEntry(R.id.productsFragment)
            val savedStateHandle = navBackStarEntry.savedStateHandle
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME && savedStateHandle.contains(PRODUCT_KEY)) {
                    val product = savedStateHandle.get<Product>(PRODUCT_KEY)
                    val oldList = productsAdapter.differ.currentList
                    val newList = oldList.toMutableList().apply {
                        add(product)
                    }
                    productsAdapter.differ.submitList(newList)
                    binding.recyclerProducts.smoothScrollToPosition(newList.size -1)
                    savedStateHandle.remove<Product>(PRODUCT_KEY)
                }
            }

            navBackStarEntry.lifecycle.addObserver(observer)

            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    navBackStarEntry.lifecycle.removeObserver(observer)
                }
            })
        }
    }

    private fun observe() {
        viewModel.productsData.observe(viewLifecycleOwner) { products ->
            productsAdapter.differ.submitList(products)
        }
        viewModel.addButtonVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.fabAdd.visibility = visibility
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}