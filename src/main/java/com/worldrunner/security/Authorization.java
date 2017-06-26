package com.worldrunner.security;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */

import com.google.gson.Gson;
import com.worldrunner.Cnst;
import com.worldrunner.dao.AuthenticationDaoImpl;
import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.spi.metadata.ResourceMethod;
import org.jboss.resteasy.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
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
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());
    ;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());
    ;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());
    ;
    private static Helper helper;

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
        String userRole = "ADMIN";

        //Step 2. Verify user role
        if (rolesSet.contains(userRole)) {
            isAllowed = true;
        }
        return isAllowed;
    }

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker resourceMethodInvoker) throws Failure, WebApplicationException {

        Method method = resourceMethodInvoker.getMethod();

        //Access allowed for all
        if (method.isAnnotationPresent(PermitAll.class)) {
            return null;
        }
        //Access denied for all
        if (method.isAnnotationPresent(DenyAll.class)) {
            return ACCESS_FORBIDDEN;
        }

        //Get request headers
        final HttpHeaders headers = request.getHttpHeaders();

        //Fetch authorization header
        final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_PROPERTY);

        //If no authorization information present; block access
        if (authorization == null || authorization.isEmpty()) {
            return ACCESS_DENIED;
        }

        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword;
        try {
            usernameAndPassword = new String(Base64.decode(encodedUserPassword));
        } catch (IOException e) {
            return SERVER_ERROR;
        }

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        String password = null;
        User user;
        Session sesion;

        try {

            AuthenticationDaoImpl dao = new AuthenticationDaoImpl();
            password = Helper.cryptPassword(tokenizer.nextToken());
            Object[] object = dao.authenticate(new User(username, password));
            user = (User) object[0];
            sesion = (Session) object[1];

        } catch (CustomException e) {

            MyResponse<?> myResponse = new MyResponse<>();
            myResponse.setCode(e.getCode());
            myResponse.setMessage(e.getMessage());
            myResponse.setStatus(Cnst.FAIL);
            myResponse.setError(2025);
            return new ServerResponse(myResponse, myResponse.getCode(), new Headers<Object>());
        }

        //Verifying Username and password
        System.out.println("user:" + username + " password:" + password);



       /* //Verify user access
        if(method.isAnnotationPresent(RolesAllowed.class))
        {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            //Is user valid?
            if( ! isUserAllowed(username, password, rolesSet))
            {
                return ACCESS_DENIED;
            }
        }*/

        //Return null to continue request processing

        return new ServerResponse(sesion, 200, new Headers<Object>());

    }
}
