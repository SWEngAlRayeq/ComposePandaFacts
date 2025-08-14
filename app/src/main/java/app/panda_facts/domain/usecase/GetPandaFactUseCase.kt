package app.panda_facts.domain.usecase

import app.panda_facts.domain.repo.PandaRepository
import javax.inject.Inject

class GetPandaFactUseCase @Inject constructor(
    private val repository: PandaRepository
) {
    suspend operator fun invoke() = repository.fetchPandaFact()
}