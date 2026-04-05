package org.webtrader.schedule.orderpositionactivation.json.util;

import com.google.gson.*;
import org.webtrader.schedule.orderpositionactivation.model.Account;

import java.lang.reflect.Type;

public class AccountJsonUtil
{
    private static final Gson prettyGson = new GsonBuilder()
            .registerTypeHierarchyAdapter( Account.class, new AccountSerializer() )
            .setPrettyPrinting()
            .create();

    public static String toPrettyJson( Account account )
    {
        return prettyGson.toJson( account );
    }

    private static class AccountSerializer implements JsonSerializer<Account>
    {
        @Override
        public JsonElement serialize( Account src, Type typeOfSrc, JsonSerializationContext context )
        {
            JsonObject json = new JsonObject();

            json.addProperty("id", src.getId());
            json.addProperty("user_id", src.getUserId());
            json.addProperty("currency_id", src.getCurrencyId());
            json.addProperty("balance", src.getBalance());
            json.addProperty("credit", src.getCredit());
            json.addProperty("leverage", src.getLeverage());
            json.addProperty("type", src.getType());
            json.addProperty("equity", src.getEquity());
            json.addProperty("margin", src.getMargin());
            json.addProperty("free_margin", src.getFreeMargin());
            json.addProperty("margin_indicator", src.getMarginIndicator());
            json.addProperty("margin_level", src.getMarginLevel());
            json.addProperty("profit_loss", src.getProfitLoss());
            json.addProperty("profit_loss_opened_orders", src.getProfitLossOpenedOrders());
            json.addProperty("status", src.getStatus());
            json.addProperty("login", src.getLogin());
            json.addProperty("uuid", src.getUuid());
            json.addProperty("last_login_time", toStringOrNull(src.getLastLoginTime()));
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
