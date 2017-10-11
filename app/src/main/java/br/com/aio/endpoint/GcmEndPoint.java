package br.com.aio.endpoint;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by elton on 11/10/2017.
 */
public interface GcmEndPoint {

    @FormUrlEncoded
    @POST("/usuario/apikey")
    Response criarChamado(@Header("Authorization") String authorization,
                          @Field("hash") String hash,
                          @Field("login") String login);
}
