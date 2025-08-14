package app.panda_facts.data.remote

import app.panda_facts.data.model.PandaDto
import retrofit2.http.GET

interface PandaApi {

    @GET("animal/panda")
    suspend fun getPandaFact(): PandaDto

}