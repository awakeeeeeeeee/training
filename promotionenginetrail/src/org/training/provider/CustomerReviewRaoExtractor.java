package org.training.provider;

import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.providers.RAOFactsExtractor;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

public class CustomerReviewRaoExtractor implements RAOFactsExtractor {

    public static final String EXPAND_CUSTOMER_REVIEWS = "EXPAND_CUSTOMER_REVIEWS";

    @Override
    public Set<?> expandFact(final Object fact)
    {
        checkArgument(fact instanceof CartRAO, "CartRAO type is expected here)");
        final Set<Object> facts = new HashSet<>();
        final CartRAO cartRAO = (CartRAO) fact;
        if (nonNull(cartRAO.getUser()))
        {
            facts.addAll(cartRAO.getUser().getCustomerReviews());
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
