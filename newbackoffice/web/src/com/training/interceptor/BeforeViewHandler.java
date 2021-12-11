/**
 *
 */
package com.training.interceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author maxyang
 *
 */
public interface BeforeViewHandler
{
	void beforeView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception;

}
