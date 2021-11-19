package org.training.populator;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.ruleengineservices.converters.populator.AbstractOrderRaoPopulator;
import de.hybris.platform.ruleengineservices.rao.AbstractOrderRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Set;

public class TrainingOrderRaoPopulator<T extends AbstractOrderModel, P extends AbstractOrderRAO> extends AbstractOrderRaoPopulator {

    private SearchWordRaoPupolator searchWordRaoPupolator;

    @Override
    public void populate(AbstractOrderModel source, AbstractOrderRAO target) {
        super.populate(source, target);

        List<AbstractOrderEntryModel> entries = source.getEntries();
        Set<OrderEntryRAO> entryRAOS = target.getEntries();
        if(CollectionUtils.isNotEmpty(entries)){
            for (AbstractOrderEntryModel entry : entries) {
                if(CollectionUtils.isNotEmpty(entryRAOS)){
                    for (OrderEntryRAO entryRAO : entryRAOS) {
                        if(entry.getProduct().equals(entryRAO.getProduct())){
                            searchWordRaoPupolator.populate(entry.getProduct(),entryRAO.getProduct());
                        }
                    }
                }
            }
        }
    }


    public void setSearchWordRaoPupolator(SearchWordRaoPupolator searchWordRaoPupolator) {
        this.searchWordRaoPupolator = searchWordRaoPupolator;
    }
}
