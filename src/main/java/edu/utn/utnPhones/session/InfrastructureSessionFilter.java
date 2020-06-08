package edu.utn.utnPhones.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static edu.utn.utnPhones.utils.Constants.INFRASTRUCTURE_SESSION;

@Service
public class InfrastructureSessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
                                    throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");
        Session session = null;
        session = sessionManager.getSession(sessionToken);

        if (null != session){
            if(INFRASTRUCTURE_SESSION.equals(sessionToken))
                filterChain.doFilter(request, response);
            else
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else
            response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
