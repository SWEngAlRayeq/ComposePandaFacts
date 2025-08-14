package app.panda_facts.data.repo_impl

import app.panda_facts.data.remote.PandaApi
import app.panda_facts.domain.model.Panda
import app.panda_facts.domain.repo.PandaRepository
import javax.inject.Inject

class PandaRepositoryImpl @Inject constructor(
    private val api: PandaApi
) : PandaRepository {
    override suspend fun fetchPandaFact(): Result<Panda> {
        return try {
            val dto = api.getPandaFact()
            val fact = dto.fact?.takeIf { it.isNotBlank() } ?: "Pandas are quietly amazing."
            Result.success(Panda(fact = fact, imageUrl = dto.image))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}