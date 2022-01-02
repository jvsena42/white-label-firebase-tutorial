package br.com.douglasmotta.whitelabeltutorial.ui.products.addProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.douglasmotta.whitelabeltutorial.R
import br.com.douglasmotta.whitelabeltutorial.databinding.AddProductFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductFragment : BottomSheetDialogFragment() {

    private lateinit var binding: AddProductFragmentBinding
    private lateinit var viewModel: AddProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_product_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddProductFragmentBinding.bind(view)
    }

}