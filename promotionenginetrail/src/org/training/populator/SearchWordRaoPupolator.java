package org.training.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ruleengineservices.converters.populator.ProductRaoPopulator;
import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class SearchWordRaoPupolator<ProductModel, ProductRAO> extends ProductRaoPopulator {

    @Override
    public void populate(de.hybris.platform.core.model.product.ProductModel source, de.hybris.platform.ruleengineservices.rao.ProductRAO target) {
        super.populate(source, target);
        target.setDescription(source.getDescription());

    }
}
