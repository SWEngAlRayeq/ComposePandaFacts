package app.panda_facts.di

import app.panda_facts.data.repo_impl.PandaRepositoryImpl
import app.panda_facts.domain.repo.PandaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPandaRepository(impl: PandaRepositoryImpl): PandaRepository
}