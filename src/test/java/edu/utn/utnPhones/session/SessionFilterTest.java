package edu.utn.utnPhones.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SessionFilterTest implements FactorySession{

    @Mock
    private SessionManager sessionManager;

    private SessionFilter sessionFilter;

    @Before
    public void setUp(){
        initMocks(this);
        this.sessionFilter = new SessionFilter(sessionManager);
    }

    @Test
    public void doFilterInternalOk() throws ServletException, IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(sessionManager.getSession("1234")).thenReturn(new Session("1234", createUser(), new Date(System.currentTimeMillis())));

        sessionFilter.doFilterInternal(request, response, filterChain);

        Assert.assertNotNull(sessionManager.getSession("1234"));
    }

    @Test
    public void doFilterInternalForbidden() throws ServletException, IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(sessionManager.getSession("1234")).thenReturn(null);

        sessionFilter.doFilterInternal(request, response, filterChain);

        Assert.assertNull(sessionManager.getSession("1234"));
    }
}
