package org.webtrader.schedule.orderpositionactivation.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Account
{
    private long id;
    private Long userId;
    private long currencyId;
    private BigDecimal balance;
    private BigDecimal credit;
    private long leverage;
    private short type;
    private BigDecimal equity;
    private BigDecimal margin;
    private BigDecimal freeMargin;
    private BigDecimal marginIndicator;
    private BigDecimal marginLevel;
    private BigDecimal profitLoss;
    private BigDecimal profitLossOpenedOrders;
    private short status;
    private String login;
    private String uuid;
    private Timestamp lastLoginTime;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public Account(ResultSet rs) throws SQLException
    {
        this.id = rs.getInt("id");
        this.userId = rs.getLong("user_id");
        this.currencyId = rs.getInt("currency_id");
        this.balance = rs.getBigDecimal("balance");
        this.credit = rs.getBigDecimal("credit");
        this.leverage = rs.getInt("leverage");
        this.type = rs.getShort("type");
        this.equity = rs.getBigDecimal("equity");
        this.margin = rs.getBigDecimal("margin");
        this.freeMargin = rs.getBigDecimal("free_margin");
        this.marginIndicator = rs.getBigDecimal("margin_indicator");
        this.marginLevel = rs.getBigDecimal("margin_level");
        this.profitLoss = rs.getBigDecimal("profit_loss");
        this.profitLossOpenedOrders = rs.getBigDecimal("profit_loss_opened_orders");
        this.status = rs.getShort("status");
        this.login = rs.getString("login");
        this.uuid = rs.getString("uuid");
        this.lastLoginTime = rs.getTimestamp("last_login_time");
        this.createdAt = rs.getTimestamp("created_at");
        this.updatedAt = rs.getTimestamp("updated_at");
        this.deletedAt = rs.getTimestamp("deleted_at");
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public long getCurrencyId() { return currencyId; }
    public void setCurrencyId(long currencyId) { this.currencyId = currencyId; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public BigDecimal getCredit() { return credit; }
    public void setCredit(BigDecimal credit) { this.credit = credit; }

    public long getLeverage() { return leverage; }
    public void setLeverage(long leverage) { this.leverage = leverage; }

    public short getType() { return type; }
    public void setType(short type) { this.type = type; }

    public BigDecimal getEquity() { return equity; }
    public void setEquity(BigDecimal equity) { this.equity = equity; }

    public BigDecimal getMargin() { return margin; }
    public void setMargin(BigDecimal margin) { this.margin = margin; }

    public BigDecimal getFreeMargin() { return freeMargin; }
    public void setFreeMargin(BigDecimal freeMargin) { this.freeMargin = freeMargin; }

    public BigDecimal getMarginIndicator() { return marginIndicator; }
    public void setMarginIndicator(BigDecimal marginIndicator) { this.marginIndicator = marginIndicator; }

    public BigDecimal getMarginLevel() { return marginLevel; }
    public void setMarginLevel(BigDecimal marginLevel) { this.marginLevel = marginLevel; }

    public BigDecimal getProfitLoss() { return profitLoss; }
    public void setProfitLoss(BigDecimal profitLoss) { this.profitLoss = profitLoss; }

    public BigDecimal getProfitLossOpenedOrders() { return profitLossOpenedOrders; }
    public void setProfitLossOpenedOrders(BigDecimal profitLossOpenedOrders) { this.profitLossOpenedOrders = profitLossOpenedOrders; }

    public short getStatus() { return status; }
    public void setStatus(short status) { this.status = status; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public Timestamp getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(Timestamp lastLoginTime) { this.lastLoginTime = lastLoginTime; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public Timestamp getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Timestamp deletedAt) { this.deletedAt = deletedAt; }
}
