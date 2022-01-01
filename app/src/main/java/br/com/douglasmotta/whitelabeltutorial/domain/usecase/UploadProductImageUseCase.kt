package br.com.douglasmotta.whitelabeltutorial.domain.usecase

interface UploadProductImageUseCase {

    suspend operator fun invoke(imageUri: Uri) : String
}