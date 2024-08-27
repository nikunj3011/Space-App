package space.repository

import kotlinx.coroutines.flow.Flow
import space.models.db.Bangboo
import space.util.Resource

interface BangbooRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Bangboo>>>

    suspend fun getMovie(id: Int): Flow<Resource<Bangboo>>
}