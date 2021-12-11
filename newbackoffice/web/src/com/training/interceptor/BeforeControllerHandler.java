/**
 *
 */
package com.training.interceptor;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author maxyang
 *
 */
public interface BeforeControllerHandler
{
	boolean beforeController(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception;

}
