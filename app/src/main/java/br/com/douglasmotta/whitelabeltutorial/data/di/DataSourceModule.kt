package br.com.douglasmotta.whitelabeltutorial.data.di

import br.com.douglasmotta.whitelabeltutorial.data.FirebaseProductDataSource
import br.com.douglasmotta.whitelabeltutorial.data.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //all the components use this instance as long the lifecycle is alive
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindProductDataSource(datasource: FirebaseProductDataSource): ProductDataSource
}