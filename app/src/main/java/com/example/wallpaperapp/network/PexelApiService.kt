import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApiService {
    @GET("v1/search")
    suspend fun getWallpapers(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("per_page") per_page: Int
    ): PexelsResponse
}
