import de.hybris.platform.processengine.model.BusinessProcessModel
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.SearchResult
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel
import de.hybris.platform.solrfacetsearch.model.config.SolrIndexedTypeModel
import org.springframework.util.CollectionUtils


flexibleSearchService  = spring.getBean("flexibleSearchService");

sql = "select {pk} from {SolrFacetSearchConfig}";

FlexibleSearchQuery query = new FlexibleSearchQuery(sql);

SearchResult<SolrFacetSearchConfigModel> searchResult = flexibleSearchService.search(query);

List<SolrFacetSearchConfigModel> solrFacetSearchConfigModelList = searchResult.getResult();

for(SolrFacetSearchConfigModel solrFacetSearchConfigModel : solrFacetSearchConfigModelList){

    println "solr facet search config name: " + solrFacetSearchConfigModel.getName()
    println "solr facet search index name prefix: " + solrFacetSearchConfigModel.getIndexNamePrefix()
    println "solr server config name: " + solrFacetSearchConfigModel.getSolrServerConfig().getName()
    println "solr index config name: " + solrFacetSearchConfigModel.getSolrIndexConfig().getName()
    List<SolrIndexedTypeModel> solrIndexedTypeModelList =  solrFacetSearchConfigModel.getSolrIndexedTypes();
    if(!CollectionUtils.isEmpty(solrIndexedTypeModelList)){
        for(SolrIndexedTypeModel solrIndexedTypeModel : solrIndexedTypeModelList){
            println "solr index type：" + solrIndexedTypeModel.getIdentifier();
        }
    }
    println ""
}