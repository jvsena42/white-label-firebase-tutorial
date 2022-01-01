package br.com.douglasmotta.whitelabeltutorial.domain.usecase

import br.com.douglasmotta.whitelabeltutorial.domain.model.Product

interface CreateProductUserCase {

    suspend operator fun invoke(description: String, imageUri: Uri) : Product
}