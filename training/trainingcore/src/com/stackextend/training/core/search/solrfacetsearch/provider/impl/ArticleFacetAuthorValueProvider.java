package com.stackextend.training.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractFacetValueDisplayNameProvider;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import org.apache.commons.lang3.StringUtils;

public class ArticleFacetAuthorValueProvider extends AbstractFacetValueDisplayNameProvider {

    @Override
    public String getDisplayName(SearchQuery searchQuery, IndexedProperty indexedProperty, String facetValue) {
        if(StringUtils.isBlank(facetValue)){
            return "";
        }


        return facetValue;
    }
}
