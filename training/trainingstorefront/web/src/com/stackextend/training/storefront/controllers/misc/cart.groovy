import de.hybris.platform.core.model.order.CartModel
import de.hybris.platform.core.model.product.ProductModel
import de.hybris.platform.jalo.JaloSession
import de.hybris.platform.order.CartService
import de.hybris.platform.servicelayer.search.FlexibleSearchService
import de.hybris.platform.servicelayer.search.SearchResult

//CartService cartService = spring.getBean("cartService");
//println(cartService)
//
//def sessionCart = cartService.getSessionCart();
//println sessionCart;
//println sessionCart.getSaveTime();
//println sessionCart.getSavedBy()
//println sessionCart.getPk()
//println sessionCart.getSessionId()
//
//def jalosession = JaloSession.getCurrentSession();
//println jalosession.toString()
//println jalosession.getCart().getSessionId();
//println jalosession.getCart().toString()
//println jalosession.getCart().getComposedType().getCode()


FlexibleSearchService flexibleSearchService = spring.getBean("flexibleSearchService");
String sql = "select {pk} from {product} where {code} = '23191'";
SearchResult<ProductModel> searchResult = flexibleSearchService.search(sql);
List<ProductModel> list = searchResult.getResult();
println list.size();
for(ProductModel p : list){
    println p.getCode();
    println p.getCatalogVersion().getCatalog().getName();
}
