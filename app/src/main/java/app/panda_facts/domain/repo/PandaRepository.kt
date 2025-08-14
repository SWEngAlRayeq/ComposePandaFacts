package app.panda_facts.domain.repo

import app.panda_facts.domain.model.Panda

interface PandaRepository {
    suspend fun fetchPandaFact(): Result<Panda>
}