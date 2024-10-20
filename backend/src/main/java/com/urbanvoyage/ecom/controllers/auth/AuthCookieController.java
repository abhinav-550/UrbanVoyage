package com.urbanvoyage.ecom.controllers.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthCookieController {
    public static void setAuthCookie(HttpServletResponse res,String userId, String role){
        Cookie authCookie = new Cookie("userId", userId);
        Cookie roleCookie = new Cookie("role", role);

        roleCookie.setPath("/");
        roleCookie.setMaxAge(7 * 24 * 60 * 60 );

        authCookie.setPath("/");
        authCookie.setMaxAge(7 * 24 * 60 * 60 );

        res.addCookie(authCookie);
        res.addCookie(roleCookie);
    }

    //for userId and role cookie
    public static String getAuthDetail(HttpServletRequest req, String requirement){
        Cookie[] allCookies = req.getCookies();
        if(allCookies != null){
            for(Cookie c : allCookies){
                if(requirement.equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return "";
    }

    public static void destroyAuthCookie(HttpServletResponse res){
        Cookie authCookie = new Cookie("userId", null);
        Cookie roleCookie = new Cookie("role", null);

        authCookie.setMaxAge(0);
        authCookie.setPath("/");

        roleCookie.setMaxAge(0);
        roleCookie.setPath("/");

        res.addCookie(authCookie);
        res.addCookie(roleCookie);
    }

    public static Boolean isAuthenticated(HttpServletRequest req){
        Cookie[] allCookies = req.getCookies();
        if(allCookies != null){
            for(Cookie c : allCookies){
                if("userId".equals(c.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean isAuthorized(HttpServletRequest req, String role){
        if(isAuthenticated(req)){
            Cookie[] allCookies = req.getCookies();
            if(allCookies != null){
                for(Cookie c : allCookies){
                    if("role".equals(c.getName())){
                        return role.equals(c.getValue());
                    }
                }
            }
        }
        return false;
    }
}
