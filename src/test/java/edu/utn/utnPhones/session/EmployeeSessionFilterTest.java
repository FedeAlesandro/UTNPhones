package edu.utn.utnPhones.session;

import edu.utn.utnPhones.models.User;
import edu.utn.utnPhones.models.enums.UserType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmployeeSessionFilterTest implements FactorySession{

    @Mock
    private SessionManager sessionManager;

    private EmployeeSessionFilter employeeSessionFilter;

    @Before
    public void setUp(){
        initMocks(this);
        this.employeeSessionFilter = new EmployeeSessionFilter(sessionManager);
    }

    @Test
    public void doFilterInternalOk() throws ServletException, IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);
        User user = createUser();
        user.setUserType(UserType.employee);

        given(request.getHeader("Authorization")).willReturn("1234");

        when(sessionManager.getSession("1234")).thenReturn(new Session("1234", user, new Date(System.currentTimeMillis())));

        employeeSessionFilter.doFilterInternal(request, response, filterChain);

        Assert.assertNotNull(sessionManager.getSession("1234"));
    }

    @Test
    public void doFilterInternalUnauthorized() throws ServletException, IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getHeader("Authorization")).willReturn("1234");

        when(sessionManager.getSession("1234")).thenReturn(new Session("1234", createUser(), new Date(System.currentTimeMillis())));

        employeeSessionFilter.doFilterInternal(request, response, filterChain);

        Assert.assertNotNull(sessionManager.getSession("1234"));
    }

    @Test
    public void doFilterInternalForbidden() throws ServletException, IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getHeader("Authorization")).willReturn("1234");

        when(sessionManager.getSession("1234")).thenReturn(null);

        employeeSessionFilter.doFilterInternal(request, response, filterChain);

        Assert.assertNull(sessionManager.getSession("1234"));
    }
}
