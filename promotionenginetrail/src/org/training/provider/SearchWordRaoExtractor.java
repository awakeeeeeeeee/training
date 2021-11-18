package org.training.provider;

import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.ProductRAO;
import de.hybris.platform.ruleengineservices.rao.providers.RAOFactsExtractor;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

public class SearchWordRaoExtractor implements RAOFactsExtractor {

    public static final String EXPAND_CUSTOMER_REVIEWS = "EXPAND_CUSTOMER_REVIEWS";

    @Override
    public Set<?> expandFact(final Object fact)
    {
        checkArgument(fact instanceof CartRAO, "CartRAO type is expected here)");
        final Set<Object> facts = new HashSet<>();
        final CartRAO cartRAO = (CartRAO) fact;
        Set<OrderEntryRAO> entries = cartRAO.getEntries();
        if(!CollectionUtils.isEmpty(entries)){
            for (OrderEntryRAO entry : entries) {
                ProductRAO productRAO = entry.getProduct();
                facts.add(productRAO);
            }
        }
        return facts;
    }

    @Override
    public String getTriggeringOption()
    {
        return EXPAND_CUSTOMER_REVIEWS;
    }

    @Override
    public boolean isMinOption()
    {
        return false;
    }

    @Override
    public boolean isDefault()
    {
        return true;
    }
}
