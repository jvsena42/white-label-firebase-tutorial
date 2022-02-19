package br.com.douglasmotta.whitelabeltutorial.domain.di

import br.com.douglasmotta.whitelabeltutorial.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class) //use the ViewModel lifecycle
interface DomainModule {

    @Binds
    fun bindCreateProductUseCase(useCaseImpl: CreateProductUseCaseImpl): CreateProductUseCase

    @Binds
    fun bindGetProductUseCase(useCaseImpl: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    fun bindUploadProductUseCase(useCaseImpl: UploadProductImageUseCaseImpl): UploadProductImageUseCase

}