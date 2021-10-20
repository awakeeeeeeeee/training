/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.training.setup;

import static com.training.constants.NewbackofficeConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.training.constants.NewbackofficeConstants;
import com.training.service.NewbackofficeService;


@SystemSetup(extension = NewbackofficeConstants.EXTENSIONNAME)
public class NewbackofficeSystemSetup
{
	private final NewbackofficeService newbackofficeService;

	public NewbackofficeSystemSetup(final NewbackofficeService newbackofficeService)
	{
		this.newbackofficeService = newbackofficeService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		newbackofficeService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return NewbackofficeSystemSetup.class.getResourceAsStream("/newbackoffice/sap-hybris-platform.png");
	}
}
