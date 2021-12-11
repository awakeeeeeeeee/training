/**
 *
 */
package com.training.interceptor;

import de.hybris.platform.servicelayer.session.SessionService;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author maxyang
 *
 */
public class CheckLoginBeforeControllerHandler implements BeforeControllerHandler
{

	@Resource
	SessionService sessionService;

	@Override
	public boolean beforeController(final HttpServletRequest request, final HttpServletResponse response,
			final HandlerMethod handler) throws Exception
	{
		final String backendUser = sessionService.getAttribute("backendUser");
		if (backendUser == null)
		{
			response.sendRedirect("/");
			return false;
		}
		else
		{
			return true;
		}


	}

}
