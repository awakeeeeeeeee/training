package org.training.action;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.promotionengineservices.action.impl.AbstractRuleActionStrategy;
import de.hybris.platform.promotionengineservices.model.RuleBasedOrderAdjustTotalActionModel;
import de.hybris.platform.promotions.model.PromotionResultModel;
import de.hybris.platform.ruleengineservices.rao.AbstractRuleActionRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class DefaultRuleSearchWordAddScoreActionStrategy extends AbstractRuleActionStrategy<RuleBasedOrderAdjustTotalActionModel> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRuleSearchWordAddScoreActionStrategy.class);

    @Override
    public List apply(AbstractRuleActionRAO action) {

        DiscountRAO discountRAO  = (DiscountRAO) action;
        final PromotionResultModel promoResult = getPromotionActionService().createPromotionResult(action);
        final AbstractOrderModel order = promoResult.getOrder();
        if (order == null)
        {
            LOG.error("cannot apply {}, order not found", this.getClass().getSimpleName());
            // detach the promotion result if its not saved yet.
            if (getModelService().isNew(promoResult))
            {
                getModelService().detach(promoResult);
            }
            return Collections.emptyList();
        }

        DiscountRAO discountRao = (DiscountRAO)action;
        //默认的扣减固定值的action
        RuleBasedOrderAdjustTotalActionModel actionModel = this.createOrderAdjustTotalAction(promoResult, discountRao);
        this.handleActionMetadata(action, actionModel);
        this.getPromotionActionService().createDiscountValue(discountRao, actionModel.getGuid(), order);
        this.getModelService().saveAll(new Object[]{promoResult, actionModel, order});
        this.recalculateIfNeeded(order);
        return Collections.singletonList(promoResult);

    }

    protected RuleBasedOrderAdjustTotalActionModel createOrderAdjustTotalAction(PromotionResultModel promoResult, DiscountRAO discountRao) {
        RuleBasedOrderAdjustTotalActionModel actionModel = (RuleBasedOrderAdjustTotalActionModel)this.createPromotionAction(promoResult, discountRao);
        actionModel.setAmount(discountRao.getValue());
        return actionModel;
    }

    @Override
    public void undo(ItemModel itemModel) {

    }
}
