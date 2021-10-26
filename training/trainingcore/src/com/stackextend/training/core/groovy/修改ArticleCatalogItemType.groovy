package com.stackextend.training.core.groovy

import de.hybris.bootstrap.ddl.model.ComposedType
import de.hybris.platform.catalog.model.CatalogVersionModel
import de.hybris.platform.core.model.type.AttributeDescriptorModel
import de.hybris.platform.core.model.type.ComposedTypeModel
import de.hybris.platform.servicelayer.model.ModelService
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.FlexibleSearchService
import de.hybris.platform.servicelayer.search.SearchResult
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedTypeModel


FlexibleSearchService flexibleSearchService = spring.getBean("flexibleSearchService");
ModelService modelService = spring.getBean("modelService");

String sql = "select {pk} from {SolrIndexedType} ";
FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
SearchResult<SolrIndexedTypeModel> searchResult = flexibleSearchService.search(query);
List<SolrIndexedTypeModel> list = searchResult.getResult();

//修改article catalogItemType为true,否则搜索商品会报错，FacetSearchQueryCatalogVersionsFilterPopulator 31行会报空指针，所以先手动调整，后续再排查为什么article的
//catalogItemType为空，不是true

AttributeDescriptorModel attributeDescriptorModel = null;
for(SolrIndexedTypeModel solrIndexedTypeModel : list){
    println "identifier: " + solrIndexedTypeModel.getIdentifier();
    ComposedTypeModel composedTypeModel = solrIndexedTypeModel.getType();
    println "code: " + composedTypeModel.getCode();
    println composedTypeModel.getCatalogItemType();
//    composedTypeModel.setCatalogItemType(true)
//    modelService.save(composedTypeModel)
//
//    AttributeDescriptorModel catalogAttDesc = composedTypeModel.getCatalogVersionAttribute();
//    composedTypeModel.getCatalogItemType();
//    println "catalogAttDesc: " +catalogAttDesc;
//    if(catalogAttDesc != null){
//        attributeDescriptorModel = catalogAttDesc;
//        println attributeDescriptorModel
//    }else {
//        println "attributeDescriptorModel null , identifier: " + solrIndexedTypeModel.getIdentifier();
//        println attributeDescriptorModel
//        composedTypeModel.setCatalogVersionAttribute(attributeDescriptorModel);
//        modelService.save(composedTypeModel)
//    }

}
//8796219605079




