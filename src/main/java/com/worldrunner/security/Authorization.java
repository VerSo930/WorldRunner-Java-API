package com.worldrunner.security;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */

import com.worldrunner.model.MyResponse;
import com.worldrunner.tools.CustomException;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 */

@Provider
@ServerInterceptor
public class Authorization implements PreProcessInterceptor {
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse(new MyResponse<String>("fail",2500, 401, "Access denied", null), 401, new Headers<Object>());
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(new MyResponse<String>("fail",2500, 401, "No way to access this resource", null), 403, new Headers<Object>());

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker resourceMethodInvoker) throws Failure, WebApplicationException {
        Method method = resourceMethodInvoker.getMethod();

        // Return null to continue request processing
        if(method.isAnnotationPresent(PermitAll.class))
        {
            return null;
        }

        // Get roles annotation
        RolesAllowed roles = method.getAnnotation(RolesAllowed.class);
        Set<String> rolesSet = new HashSet<String>(Arrays.asList(roles.value()));

        // Access allowed for all
        if (method.isAnnotationPresent(PermitAll.class)) {
            return null;
        }
        //Access denied for all
        if (method.isAnnotationPresent(DenyAll.class)) {
            return ACCESS_FORBIDDEN;
        }

        // Get request headers
        final HttpHeaders headers = request.getHttpHeaders();

        // Fetch authorization header
        final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_PROPERTY);

        // If no authorization information present; block access
        if (authorization == null || authorization.isEmpty()) {
            return ACCESS_DENIED;
        }

        // Get token
        final String token = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        // Verify token signature
        try {
            Tokenizer.verifyJWT(token, rolesSet);
        } catch (CustomException e) {
           return new ServerResponse(new MyResponse<String>("fail",2500, 401, e.getMessage(), null), 401, new Headers<Object>());
        }

        // Return null and continue request processing
        return null;

    }

}
