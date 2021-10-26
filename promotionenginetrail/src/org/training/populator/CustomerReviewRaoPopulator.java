package org.training.populator;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.promotionenginetrail.rao.CustomerReviewRAO;
import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;
import de.hybris.platform.converters.Populator;


public class CustomerReviewRaoPopulator implements Populator<CustomerReviewModel, CustomerReviewRAO> {


    private Converter<ProductModel, ProductRAO> productConverter;

    @Override
    public void populate(final CustomerReviewModel source, final CustomerReviewRAO target) throws ConversionException
    {
        target.setProduct(productConverter.convert(source.getProduct()));
    }


    public Converter<ProductModel, ProductRAO> getProductConverter()
    {
        return productConverter;
    }

    @Required
    public void setProductConverter(final Converter<ProductModel, ProductRAO> productConverter)
    {
        this.productConverter = productConverter;
    }
}
