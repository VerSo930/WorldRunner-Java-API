package com.worldrunner.security;

/**
 * Created by Vuta Alexandru on 6/24/2017.
 */

import com.worldrunner.Cnst;
import com.worldrunner.dao.AuthenticationDaoImpl;
import com.worldrunner.model.Authentication.AuthorizationResponse;
import com.worldrunner.model.Authentication.Session;
import com.worldrunner.model.MyResponse;
import com.worldrunner.model.User;
import com.worldrunner.tools.CustomException;
import com.worldrunner.tools.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
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
    private static final ServerResponse ACCESS_DENIED = new ServerResponse(new MyResponse<String>("fail",2500, 401, "Access denied", null), 401, new Headers<Object>());
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(new MyResponse<String>("fail",2500, 401, "No way to access this resource", null), 403, new Headers<Object>());
    private static final ServerResponse SERVER_ERROR = new ServerResponse(new MyResponse<String>("fail",4500, 401, "Internal server error", null), 500, new Headers<Object>());
    private Helper helper;
    private MyResponse<AuthorizationResponse> response;
    private User user;
    private Session session;

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
        response = new MyResponse<AuthorizationResponse>();
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
        Session sesion;
/*
        try {

            AuthenticationDaoImpl dao = new AuthenticationDaoImpl();
            password = Helper.cryptPassword(tokenizer.nextToken());
            sesion = new Session();
            //sesion.setToken();

            response = new MyResponse<>();
            //response.setData(dao.authenticate(new User(username, password)));
            response.setCode(200);
            response.setMessage("authentication successfully ");
            response.setStatus(Cnst.SUCCESS);

        } catch (CustomException e) {

            response.setData(null);
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
            response.setStatus(Cnst.FAIL);
            response.setError(2025);
        }*/

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


       // return new ServerResponse(response, response.getCode(), new Headers<>());
        return null;

    }



    private  String generateRefreshJWT(String sessionId) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setIssuer("WorldRunner API")
                .setSubject("Token2")
                .claim("sessionId", sessionId)
                .claim("iat", date)
                .claim("exp", helper.addMinutesToCurrentDate(60))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode("pCu/ghCamq9+wS/CG16JJ1NBqur2Ckzl522AA8xbhSQ=")
                )
                .compact();
    }

    private  boolean checkTokenSignature(String jwt) {
        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(Cnst.JWT_SECRET))
                    .parseClaimsJws(jwt).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
