package br.com.aio.exception;

import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class AcessoNegadoException extends RuntimeException {

    private Response response;

    public AcessoNegadoException(Response response){
        this.response = response;
    }
    public AcessoNegadoException(){}

    public Response getResponse() {
        return response;
    }
}
