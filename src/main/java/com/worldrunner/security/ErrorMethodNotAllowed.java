
package com.worldrunner.security;

import com.worldrunner.model.MyResponse;
import org.apache.http.MethodNotSupportedException;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Created by vuta on 15/06/2017.
 */

@Provider
public class ErrorMethodNotAllowed implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException e) {
        MyResponse<String>  response = new MyResponse<String>();
        response.setCode(Response.Status.BAD_REQUEST.getStatusCode());
        response.setData(null);
        response.setError(4500);
        response.setStatus("fail");
        response.setMessage("method not allowed");
        return Response.status(Response.Status.BAD_REQUEST).
                entity(response).type("application/json").
                build();
    }
}
