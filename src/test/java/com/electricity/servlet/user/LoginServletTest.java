package com.electricity.servlet.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.path.AppViewPath.LOGIN;

//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;

//@PrepareForTest(LoggingService.class)
class LoginServletTest {

    private LoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void preparedToEachTest() {

        this.servlet = new LoginServlet();
        this.request = Mockito.mock(HttpServletRequest.class);
        this.response = Mockito.mock(HttpServletResponse.class);
        this.dispatcher = Mockito.mock(RequestDispatcher.class);
    }

    @Test
    void should_validate_method_doGet() throws ServletException, IOException {
        //Given
        BDDMockito.given(request.getRequestDispatcher(LOGIN.getPath())).willReturn(dispatcher);

        //When
        servlet.doGet(request, response);

        //Then
        BDDMockito.verify(request, Mockito.times(1)).getRequestDispatcher(LOGIN.getPath());
        BDDMockito.verify(request, Mockito.never()).getSession();
        BDDMockito.verify(dispatcher).forward(request, response);
    }
}