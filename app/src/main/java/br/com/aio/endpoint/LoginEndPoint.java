package br.com.aio.endpoint;

import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by elton on 11/10/2017.
 */

public interface LoginEndPoint {

    // @GET("/webservices/rest.php?method=glpi.doLogin")
    // Response login(@Query("login_name") String name,@Query("login_password") String password);

    @FormUrlEncoded
    @POST("/oauth/token")
    Response login(@Field("username") String name,
                   @Field("password") String password,
                   @Field("scope")  String scope,
                   @Field("client_id") String clientId,
                   @Field("client_secret") String clientSecret,
                   @Field("grant_type") String grantType );

    @FormUrlEncoded
    @POST("/usuario/novo")
    Response cadastrarUsuario(@Field("login") String name,
                              @Field("senha") String password,
                              @Field("cpf")  String scope);

    @GET("/apisme/api")
    Response accessResource(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/usuario/resgatar/senha")
    Response solicitarAlteracaoSenha(@Field("senha")  String senha,
                                     @Field("cpf")  String cpf);

    @FormUrlEncoded
    @POST("/usuario/editar")
    Response atualizarUsuario(@Header("Authorization") String authorization,
                              @Field("id") String id,
                              @Field("login") String login,
                              @Field("cpf") String cpf,
                              @Field("senha") String senha);

    @DELETE("/usuario/logout/{login}")
    Response logout(@Header("Authorization") String authorization,
                    @Path("login") String login);
}

