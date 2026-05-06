package edu.espe.springlab.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    /* Tomar el tiempo de inicio */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        req.setAttribute("t0", System.currentTimeMillis());
        return true;
    }

    /* Agregar el header a la respuesta antes de que termine */
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView) {
        Long t0 = (Long) req.getAttribute("t0");
        if (t0 != null) {
            long elapsed = System.currentTimeMillis() - t0;
            resp.addHeader("X-Elapsed-Time", elapsed + "ms");
        }
    }

    /* Registrar en consola al finalizar */
    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        Long t0 = (Long) req.getAttribute("t0");
        long elapsed = (t0 == null) ? -1 : (System.currentTimeMillis() - t0);

        /*Registrar método, URI, código de estado y tiempo total*/
        System.out.println("Método: " + req.getMethod() + " URI: " + req.getRequestURI() + " Status: " + resp.getStatus() +  " Tiempo: " + elapsed + "ms");
    }
}