package com.electricity.servlet.user;

import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.electricity.enumeration.AppViewPath.HOME;
import static org.mockito.Mockito.*;

class HomeServletTest {

    @Test
    void should_validate_method_doGet() throws ServletException, IOException {
        HomeServlet servlet = new HomeServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(HOME.getPath())).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(HOME.getPath());
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
}