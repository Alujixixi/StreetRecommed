package com.aluji.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginHandlerInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws ServletException, IOException {
            Object user = request.getSession().getAttribute("loginUser");
            if(user == null){
                //未登录
                request.setAttribute("loginmsg","没有权限，请先登录");
                request.getRequestDispatcher("/index.html").forward(request,response);

                return false;
            }
            else{
                return true;
            }
        }
}
