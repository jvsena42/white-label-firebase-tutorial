package br.com.douglasmotta.whitelabeltutorial.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.douglasmotta.whitelabeltutorial.databinding.ItemProductBinding
import br.com.douglasmotta.whitelabeltutorial.domain.model.Product
import br.com.douglasmotta.whitelabeltutorial.util.toCurrency
import com.bumptech.glide.Glide

class ProductsAdapter: RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>()  {

    private val callback = object : DiffUtil.ItemCallback<Product>(){

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val post = differ.currentList[position]
        holder.bind( post)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ProductsViewHolder(private val binding:ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Product) = binding.run{

            Glide.with(itemView).load(item.imageUrl).centerCrop().into(imageProduct)
            textDescription.text = item.description
            textPrice.text = item.price.toCurrency()

            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener:((Product)->Unit)? = null

    fun setOnItemClickListener(listener:(Product)->Unit){
        onItemClickListener = listener
    }
}