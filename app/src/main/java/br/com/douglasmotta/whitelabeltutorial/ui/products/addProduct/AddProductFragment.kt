package br.com.douglasmotta.whitelabeltutorial.ui.products.addProduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.douglasmotta.whitelabeltutorial.R
import br.com.douglasmotta.whitelabeltutorial.databinding.AddProductFragmentBinding
import br.com.douglasmotta.whitelabeltutorial.util.CurrencyTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BottomSheetDialogFragment() {

    private lateinit var binding: AddProductFragmentBinding
    private val viewModel: AddProductViewModel by viewModels()

    private var imaUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imaUri = uri
        binding.imageProduct.setImageURI(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_product_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddProductFragmentBinding.bind(view)
        observeEvents()
        setListeners()
    }

    private fun observeEvents() = binding.run {
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner) {
            imageProduct.setBackgroundResource(it)
        }
        viewModel.descriptionFieldErrorResId.observe(viewLifecycleOwner) {
            inputLayoutDescription.setError(it)
        }
        viewModel.priceFieldErrorResId.observe(viewLifecycleOwner) {
            inputLayoutPrice.setError(it)
        }
    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        error = if (stringResId != null) {
            getString(stringResId)
        } else null
    }

    private fun setListeners() = binding.run {
        imageProduct.setOnClickListener {
            chooseImage()
        }

        buttonAddProduct.setOnClickListener {
            val description = inputDescription.text.toString()
            val price = inputPrice.text.toString()

            viewModel.createProduct(description, price, imaUri)
        }

        inputPrice.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }
    }

    private fun chooseImage() {
        getContent.launch("image/*")
    }

}