package org.webtrader.schedule.orderpositionactivation.domais.order_prepare;

import org.webtrader.schedule.orderpositionactivation.model.Order;
import org.webtrader.schedule.orderpositionactivation.order.constants.OrderStates;
import org.webtrader.schedule.orderpositionactivation.order.constants.OrderTypes;

import java.math.BigDecimal;

public class OrderPositionActivator
{
    private final OrderCloseContext context;

    public OrderPositionActivator( OrderCloseContext context )
    {
        this.context = context;
    }

    public void closeOrder( Order order )
    {
        BigDecimal closePrice = getClosePrice( order.getType() );
        BigDecimal profitLoss = calculateProfitLoss( order, closePrice );

        order.setClosePrice( closePrice );
        order.setProfitLoss( profitLoss );
        order.setState( OrderStates.HISTORY );
        order.setClosingDate( context.getCurrentTimestamp() );
        order.setUpdatedAt( context.getCurrentTimestamp() );
    }

    private BigDecimal getClosePrice( byte type )
    {
        return BigDecimal.valueOf( type == OrderTypes.BUY ? context.getBid() : context.getAsk() );
    }

    /**
     * Вычисляет прибыль/убыток (P/L) для ордера по формуле:
     *
     * Для BUY:  P/L = (closePrice - openPrice) × openRate × lots × units - commission
     * Для SELL: P/L = -(closePrice - openPrice) × openRate × lots × units - commission
     *
     * @param order ордер для расчета
     * @param closePrice цена закрытия
     * @return рассчитанный Profit/Loss (может быть отрицательным при убытке)
     */
    private BigDecimal calculateProfitLoss( Order order, BigDecimal closePrice )
    {
        BigDecimal openRate = order.getOpenRate();
        BigDecimal openPrice = order.getOpenPrice();
        BigDecimal lots = order.getLots();
        BigDecimal units = order.getUnits();
        BigDecimal commission = order.getCommission() != null ? order.getCommission() : BigDecimal.ZERO;

        BigDecimal priceDiff = closePrice.subtract( openPrice );
        if ( order.getType() == OrderTypes.SELL )
        {
            priceDiff = priceDiff.negate();
        }

        return priceDiff
                .multiply( openRate )
                .multiply( lots )
                .multiply( units )
                .subtract( commission );
    }
}
