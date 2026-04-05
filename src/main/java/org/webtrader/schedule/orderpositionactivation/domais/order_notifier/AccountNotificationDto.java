package org.webtrader.schedule.orderpositionactivation.domais.order_notifier;

import java.util.Set;

public record AccountNotificationDto(
        Set<Long> accountIDs,
        String programName
) {}
