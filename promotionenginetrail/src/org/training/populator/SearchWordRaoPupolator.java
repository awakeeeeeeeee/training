package org.training.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class SearchWordRaoPupolator implements Populator<ProductModel, ProductRAO> {

    @Override
    public void populate(ProductModel source, ProductRAO target) throws ConversionException {

        target.setDescription(source.getDescription());
    }
}
