package com.stackextend.training.core.search.solrfacetsearch.provider.impl;

import com.stackextend.training.core.enums.ArticleStatusEnum;
import com.stackextend.training.core.enums.SwatchColorEnum;
import com.stackextend.training.core.model.ArticleModel;
import de.hybris.platform.catalog.enums.ArticleStatus;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.enums.Gender;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractFacetValueDisplayNameProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ArticleStatusDisplayValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {

    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object model) throws FieldValueProviderException {

        ArticleModel articleModel = (ArticleModel) model;
        if(articleModel == null){
            return Collections.EMPTY_LIST;
        }

        final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
        fieldValues.addAll(createFieldValue(articleModel, indexedProperty));

        return fieldValues;
    }

    private Collection<? extends FieldValue> createFieldValue(ArticleModel articleModel, IndexedProperty indexedProperty) {
        final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
        final Object value = articleModel.getStatus().getCode();
        final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
        for (final String fieldName : fieldNames)
        {
            fieldValues.add(new FieldValue(fieldName, value));
        }
        return fieldValues;
    }


    public FieldNameProvider getFieldNameProvider() {
        return fieldNameProvider;
    }

    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}
