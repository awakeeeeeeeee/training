package org.training.action;

import de.hybris.platform.ruleengineservices.calculation.RuleEngineCalculationService;
import de.hybris.platform.ruleengineservices.rao.CartRAO;
import de.hybris.platform.ruleengineservices.rao.DiscountRAO;
import de.hybris.platform.ruleengineservices.rao.OrderEntryRAO;
import de.hybris.platform.ruleengineservices.rao.RuleEngineResultRAO;
import de.hybris.platform.ruleengineservices.rule.evaluation.AddOrderEntryDiscountRAOAction;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleActionContext;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleEvaluationException;
import de.hybris.platform.ruleengineservices.rule.evaluation.RuleExecutableAction;
import de.hybris.platform.ruleengineservices.rule.evaluation.actions.AbstractRuleExecutableSupport;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RuleSearchedWordAddScoreAction extends AbstractRuleExecutableSupport implements RuleExecutableAction {

    private RuleEngineCalculationService addOrderEntryDiscountRAOAction;

    @Override
    public void executeAction(final RuleActionContext context, final Map<String, Object> parameters) throws RuleEvaluationException {

        final BigDecimal value = (BigDecimal) parameters.get("value");
        final RuleEngineResultRAO result = context.getValue(RuleEngineResultRAO.class);
        final CartRAO cartRAO = context.getValue(CartRAO.class);

//        Optional<OrderEntryRAO> orderEntry = cartRAO.getEntries()
//                .stream()
//                .filter(orderEntryRAO -> orderEntryRAO.getProduct().getDescription().equalsIgnoreCase(String.valueOf(value)))
//                .findFirst();

        Set<OrderEntryRAO> entries = cartRAO.getEntries();
        for (OrderEntryRAO entry : entries) {
            if(entry.getProduct().getDescription().contains("PowerShot")){
                DiscountRAO discountRAO = this.addOrderEntryDiscountRAOAction.addOrderLevelDiscount(cartRAO, true, value);
//                getRaoUtils().addAction(cartRao, changeUserGroupRAO);
                result.getActions().add(discountRAO);
                setRAOMetaData(context, discountRAO);
//                context.insertFacts(changeUserGroupRAO, userGroupRAO);
//                context.scheduleForUpdate(user);
            }

        }
    }


    public void setAddOrderEntryDiscountRAOAction(RuleEngineCalculationService addOrderEntryDiscountRAOAction) {
        this.addOrderEntryDiscountRAOAction = addOrderEntryDiscountRAOAction;
    }
}
