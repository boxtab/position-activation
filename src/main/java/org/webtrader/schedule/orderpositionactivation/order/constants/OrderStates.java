package org.webtrader.schedule.orderpositionactivation.order.constants;

public class OrderStates
{
    public static final int POSITION = 1;

    public static final int HISTORY = 2;

    public static final int PENDING = 3;

    // Приватный конструктор предотвращает создание экземпляров этого класса
    private OrderStates()
    {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
