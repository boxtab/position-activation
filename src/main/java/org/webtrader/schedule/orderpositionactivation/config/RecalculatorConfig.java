package org.webtrader.schedule.orderpositionactivation.config;

public record RecalculatorConfig(
        String host,
        String port,
        String token
) {}
