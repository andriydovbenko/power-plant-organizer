package com.electricity.servlet.user;

import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.AppViewPath.LOGOUT;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class LogOutServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        //Given
        LogOutServlet servlet = new LogOutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        given(request.getRequestDispatcher(LOGOUT.getPath())).willReturn(dispatcher);

        //When
        servlet.doGet(request, response);

        //Then
        verify(request, times(1)).getRequestDispatcher(LOGOUT.getPath());
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
}