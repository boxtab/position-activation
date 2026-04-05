package org.webtrader.schedule.orderpositionactivation.order.constants;

public class OrderTypes
{
    public static final int BUY = 0;

    public static final int SELL = 1;

    // Приватный конструктор предотвращает создание экземпляров этого класса
    private OrderTypes()
    {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
