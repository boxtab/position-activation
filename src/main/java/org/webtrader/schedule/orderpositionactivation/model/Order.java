package org.webtrader.schedule.orderpositionactivation.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Order
{
    protected long id;
    protected byte type;
    protected Long quoteId;
    protected Long currencyId;
    protected BigDecimal lots;
    protected BigDecimal units;
    protected BigDecimal margin;
    protected Integer stopLossKey;
    protected BigDecimal stopLossValue;
    protected Integer takeProfitKey;
    protected BigDecimal takeProfitValue;
    protected LocalDateTime expired;
    protected BigDecimal activationPrice;
    protected BigDecimal openPrice;
    protected BigDecimal openRate;
    protected BigDecimal closePrice;
    protected BigDecimal closeRate;
    protected BigDecimal commission;
    protected BigDecimal swaps;
    protected BigDecimal profitLoss;
    protected int state;
    protected Long accountId;
    protected Timestamp closingDate;
    protected Timestamp createdAt;
    protected Timestamp updatedAt;
    protected Timestamp deletedAt;

    public long getId() { return id; }

    public void setId( long id ) { this.id = id; }

    public byte getType() { return type; }

    public void setType( byte type ) { this.type = type; }

    public Long getQuoteId() { return quoteId; }

    public void setQuoteId(Long quoteId) { this.quoteId = quoteId; }

    public Long getCurrencyId() { return currencyId; }

    public void setCurrencyId( Long currencyId ) { this.currencyId = currencyId; }

    public BigDecimal getLots() { return lots; }

    public void setLots( BigDecimal lots ) { this.lots = lots; }

    public BigDecimal getUnits() { return units; }

    public void setUnits( BigDecimal units ) { this.units = units; }

    public BigDecimal getMargin() { return margin; }

    public void setMargin( BigDecimal margin ) { this.margin = margin; }

    public Integer getStopLossKey() { return stopLossKey; }

    public void setStopLossKey( Integer stopLossKey ) { this.stopLossKey = stopLossKey; }

    public BigDecimal getStopLossValue() { return stopLossValue; }

    public void setStopLossValue( BigDecimal stopLossValue ) { this.stopLossValue = stopLossValue; }

    public Integer getTakeProfitKey() { return takeProfitKey; }

    public void setTakeProfitKey( Integer takeProfitKey ) { this.takeProfitKey = takeProfitKey; }

    public BigDecimal getTakeProfitValue() { return takeProfitValue; }

    public void setTakeProfitValue( BigDecimal takeProfitValue ) { this.takeProfitValue = takeProfitValue; }

    public LocalDateTime getExpired() { return expired; }

    public void setExpired( LocalDateTime expired ) { this.expired = expired; }

    public BigDecimal getActivationPrice() { return activationPrice; }

    public void setActivationPrice( BigDecimal activationPrice ) { this.activationPrice = activationPrice; }

    public BigDecimal getOpenPrice() { return openPrice; }

    public void setOpenPrice( BigDecimal openPrice ) { this.openPrice = openPrice; }

    public BigDecimal getOpenRate() { return openRate; }

    public void setOpenRate( BigDecimal openRate ) { this.openRate = openRate; }

    public BigDecimal getClosePrice() { return closePrice; }

    public void setClosePrice( BigDecimal closePrice ) { this.closePrice = closePrice; }

    public BigDecimal getCloseRate() { return closeRate; }

    public void setCloseRate( BigDecimal closeRate ) { this.closeRate = closeRate; }

    public BigDecimal getCommission() { return commission; }

    public void setCommission( BigDecimal commission ) { this.commission = commission; }

    public BigDecimal getSwaps() { return swaps; }

    public void setSwaps( BigDecimal swaps ) { this.swaps = swaps; }

    public BigDecimal getProfitLoss() { return profitLoss; }

    public void setProfitLoss( BigDecimal profitLoss ) { this.profitLoss = profitLoss; }

    public int getState() { return state; }

    public void setState( int state ) { this.state = state; }

    public Long getAccountId() { return accountId; }

    public void setAccountId( Long accountId ) { this.accountId = accountId; }

    public Timestamp getClosingDate() { return closingDate; }

    public void setClosingDate( Timestamp closingDate ) { this.closingDate = closingDate; }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setCreatedAt( Timestamp createdAt ) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt( Timestamp updatedAt ) { this.updatedAt = updatedAt; }

    public Timestamp getDeletedAt() { return deletedAt; }

    public void setDeletedAt( Timestamp deletedAt ) { this.deletedAt = deletedAt; }

    public Order( ResultSet rs ) throws SQLException
    {
        this.id = rs.getLong("id");
        this.type = (byte) rs.getInt("type");

        this.quoteId = rs.getObject("quote_id") != null
                ? rs.getLong("quote_id")
                : null;

        this.currencyId = rs.getObject("currency_id") != null
                ? rs.getLong("currency_id")
                : null;

        this.lots = rs.getObject("lots") != null
                ? rs.getBigDecimal("lots")
                : null;

        this.units = rs.getObject("units") != null
                ? rs.getBigDecimal("units")
                : null;

        this.margin = rs.getObject("margin") != null
                ? rs.getBigDecimal("margin")
                : null;

        this.stopLossKey = rs.getObject("stop_loss_key") != null
                ? rs.getInt("stop_loss_key")
                : null;

        this.stopLossValue = rs.getObject("stop_loss_value") != null
                ? rs.getBigDecimal("stop_loss_value")
                : null;

        this.takeProfitKey = rs.getObject("take_profit_key") != null
                ? rs.getInt("take_profit_key")
                : null;

        this.takeProfitValue = rs.getObject("take_profit_value") != null
                ? rs.getBigDecimal("take_profit_value")
                : null;

        Timestamp expiredTimestamp = rs.getTimestamp("expired");
        this.expired = expiredTimestamp != null
                ? expiredTimestamp.toLocalDateTime()
                : null;

        this.activationPrice = rs.getObject("activation_price") != null
                ? rs.getBigDecimal("activation_price")
                : null;

        this.openPrice = rs.getObject("open_price") != null
                ? rs.getBigDecimal("open_price")
                : null;

        this.openRate = rs.getObject("open_rate") != null
                ? rs.getBigDecimal("open_rate")
                : null;

        this.closePrice = rs.getObject("close_price") != null
                ? rs.getBigDecimal("close_price")
                : null;

        this.closeRate = rs.getObject("close_rate") != null
                ? rs.getBigDecimal("close_rate")
                : null;

        this.commission = rs.getObject("commission") != null
                ? rs.getBigDecimal("commission")
                : null;

        this.swaps = rs.getObject("swaps") != null
                ? rs.getBigDecimal("swaps")
                : null;

        this.profitLoss = rs.getObject("profit_loss") != null
                ? rs.getBigDecimal("profit_loss")
                : null;

        this.state = rs.getInt("state");

        this.accountId = rs.getObject("account_id") != null
                ? rs.getLong("account_id")
                : null;

        this.closingDate = rs.getTimestamp("closing_date");
        this.createdAt = rs.getTimestamp("created_at");
        this.updatedAt = rs.getTimestamp("updated_at");
        this.deletedAt = rs.getTimestamp("deleted_at");
    }

    @Override
    public String toString() {
        return "Order{ id=" + id +
                ", type=" + type +
                ", stopLossKey=" + stopLossKey +
                ", stopLossValue=" + stopLossValue +
                ", takeProfitKey=" + takeProfitKey +
                ", takeProfitValue=" + takeProfitValue +
                ", lots=" + lots +
                ", accountId=" + accountId +
                ", state=" + state + " }";
    }
}
