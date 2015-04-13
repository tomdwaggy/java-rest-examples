/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.arven.flare.rs;

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

/**
 * @author Brian Becker
 */
public class HttpBasicAuthenticationFilter implements Filter {

    public void init(FilterConfig fc) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if( request instanceof HttpServletRequest ) {
            HttpServletRequest req = (HttpServletRequest) request;
            String auth = req.getHeader("authorization");
            if(auth != null) {
                String[] key = new String(BaseEncoding.base64().decode(auth.substring(6))).split(":");
                if(key.length == 2) {
                    req.login(key[0], key[1]);
                }
            } else {
                req.login("anonymous", "anonymous");
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
    
}
