import com.stackextend.training.core.enums.ArticleStatusEnum
import com.stackextend.training.core.model.ArticleModel
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.FlexibleSearchService
import de.hybris.platform.servicelayer.search.SearchResult
import de.hybris.platform.solrfacetsearch.model.config.SolrFacetSearchConfigModel

flexibleSearchService = spring.getBean("flexibleSearchService");

String sql = "select {pk} from {article} ";
FlexibleSearchQuery query = new FlexibleSearchQuery(sql);
SearchResult<ArticleModel> searchResult = flexibleSearchService.search(query);
List<ArticleModel> articleModelList = searchResult.getResult();

for (ArticleModel articleModel : articleModelList) {
    //8796153348187
    println articleModel.getStatus()
    ArticleStatusEnum articleStatusEnum = articleModel.getStatus();
    println articleStatusEnum.getCode()
}