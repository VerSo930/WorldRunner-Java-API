package com.worldrunner.security;

import com.worldrunner.model.MyResponse;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by vuta on 15/06/2017.
 */
@Provider
public class ErrorMapper  implements ExceptionMapper<UnrecognizedPropertyException>
{

     @Override
    public Response toResponse(UnrecognizedPropertyException e) {

         MyResponse<String>  response = new MyResponse<>();
         response.setCode(400);
         response.setData(null);
         response.setError(4500);
         response.setStatus("fail");
         response.setMessage("json bad formatted");
        return Response.status(400).entity(response).type("application/json").build();
    }
}
