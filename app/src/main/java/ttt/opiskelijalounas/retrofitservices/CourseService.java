package ttt.opiskelijalounas.retrofitservices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ttt.opiskelijalounas.models.SodexoJSONResponse;

/**
 * Created by Teemu on 16.12.2016.
 *
 */

public interface CourseService {

    @GET("{sodexoId}/{date}/fi")
    Call<SodexoJSONResponse> courses(@Path("sodexoId") String sodexoId, @Path("date") String date);

}
