package com.electricity.servlet.user;

import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.electricity.enumeration.path.AppViewPath.HOME;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class HomeServletTest {

    @Test
    void should_validate_method_doGet() throws ServletException, IOException {
        //Given
        HomeServlet servlet = new HomeServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        given(request.getRequestDispatcher(HOME.getPath())).willReturn(dispatcher);

        //When
        servlet.doGet(request, response);

        //Then
        verify(request, times(1)).getRequestDispatcher(HOME.getPath());
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
}