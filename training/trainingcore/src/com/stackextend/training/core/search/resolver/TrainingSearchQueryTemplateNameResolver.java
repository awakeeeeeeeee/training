package com.stackextend.training.core.search.resolver;

import de.hybris.platform.commerceservices.enums.SearchQueryContext;
import de.hybris.platform.commerceservices.search.solrfacetsearch.strategies.impl.DefaultSearchQueryTemplateNameResolver;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;

public class TrainingSearchQueryTemplateNameResolver extends DefaultSearchQueryTemplateNameResolver {

    @Override
    public String resolveTemplateName(FacetSearchConfig facetSearchConfig, IndexedType indexedType, SearchQueryContext searchQueryContext) {

//        if(facetSearchConfig.getName().contains("article")){
//            return null;
//        }
        return super.resolveTemplateName(facetSearchConfig, indexedType, searchQueryContext);
    }
}
