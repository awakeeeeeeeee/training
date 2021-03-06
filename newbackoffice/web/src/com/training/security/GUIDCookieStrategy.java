package com.training.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Strategy for setting and removing a GUID cookie
 */
public interface GUIDCookieStrategy
{
	/**
	 * Generates a UID and stores it as Cookie and session attribute
	 *
	 * @param request
	 * @param response
	 */
	void setCookie(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Removes the GUID cookie
	 *
	 * @param request
	 * @param response
	 */
	void deleteCookie(HttpServletRequest request, HttpServletResponse response);
}
