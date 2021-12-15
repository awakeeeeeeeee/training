/**
 *
 */
package com.testrite.newbackoffice.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author maxyang
 *
 */
@Controller
@Scope("tenant")
public class DefaultPageController
{
	private static final Logger LOG = Logger.getLogger(DefaultPageController.class);

	@RequestMapping(method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
	{
		final String path = request.getServletPath();

		LOG.info("processing path " + path);
		if (path.equalsIgnoreCase("/"))
		{
			return "home";
		}

		if (path.indexOf('/') >= 0)
		{
			final int idx = path.lastIndexOf('/');

			if (idx < path.length() - 1)
			{
				return path.substring(idx + 1);
			}
		}
		else
		{
			return "home";
		}
		return "404";
	}

}
