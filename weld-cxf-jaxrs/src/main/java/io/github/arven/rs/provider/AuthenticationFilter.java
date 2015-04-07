/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.rs.provider;

import com.google.common.io.BaseEncoding;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian.becker
 */
@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig cfg) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
        //    HttpServletResponse res = (HttpServletResponse) response;
        //    String username = "anonymous", password = "anonymous";
        //    if(req.getHeader("Authorization") != null) {
        //        String s[] = (new String(BaseEncoding.base64().decode(req.getHeader("Authorization").split(" ")[1]))).split(":");
        //        username = s[0]; password = s[1];
        //    }
            //req.logout();
            //req.login("test", "test");
        //    req.login(username, password);
        
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
    
}