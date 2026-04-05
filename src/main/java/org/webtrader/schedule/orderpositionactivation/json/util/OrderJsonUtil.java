package org.webtrader.schedule.orderpositionactivation.json.util;

import com.google.gson.*;
import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.lang.reflect.Type;

public class OrderJsonUtil
{
    private static final Gson prettyGson = new GsonBuilder()
            .registerTypeHierarchyAdapter( Order.class, new OrderSerializer() )
            .setPrettyPrinting()
            .create();

    public static String toPrettyJson( Order order )
    {
        return prettyGson.toJson( order );
    }

    private static class OrderSerializer implements JsonSerializer<Order>
    {
        @Override
        public JsonElement serialize( Order src, Type typeOfSrc, JsonSerializationContext context )
        {
            JsonObject json = new JsonObject();

            json.addProperty("id", src.getId());
            json.addProperty("type", src.getType());
            json.addProperty("quote_id", src.getQuoteId());
            json.addProperty("currency_id", src.getCurrencyId());
            json.addProperty("lots", src.getLots());
            json.addProperty("units", src.getUnits());
            json.addProperty("margin", src.getMargin());
            json.addProperty("stop_loss_key", src.getStopLossKey());
            json.addProperty("stop_loss_value", src.getStopLossValue());
            json.addProperty("take_profit_key", src.getTakeProfitKey());
            json.addProperty("take_profit_value", src.getTakeProfitValue());
            json.addProperty("expired", toStringOrNull(src.getExpired()));
            json.addProperty("activation_price", src.getActivationPrice());
            json.addProperty("open_price", src.getOpenPrice());
            json.addProperty("open_rate", src.getOpenRate());
            json.addProperty("close_price", src.getClosePrice());
            json.addProperty("close_rate", src.getCloseRate());
            json.addProperty("commission", src.getCommission());
            json.addProperty("swaps", src.getSwaps());
            json.addProperty("profit_loss", src.getProfitLoss());
            json.addProperty("state", src.getState());
            json.addProperty("account_id", src.getAccountId());
            json.addProperty("closing_date", toStringOrNull(src.getClosingDate()));
            json.addProperty("created_at", toStringOrNull(src.getCreatedAt()));
            json.addProperty("updated_at", toStringOrNull(src.getUpdatedAt()));
            json.addProperty("deleted_at", toStringOrNull(src.getDeletedAt()));

            return json;
        }

        private String toStringOrNull( Object obj )
        {
            return obj != null ? obj.toString() : null;
        }
    }
}
