package com.worldrunner.security;

import com.worldrunner.model.MyResponse;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by vuta on 15/06/2017.
 */
@Provider
public class ErrorNotFound implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception){
        MyResponse<String>  response = new MyResponse<String>();
        response.setCode(404);
        response.setData(null);
        response.setError(4500);
        response.setStatus("fail");
        response.setMessage("method not found");
        return Response.status(Response.Status.NOT_FOUND).
                entity(response).type("application/json").
                build();
    }
}