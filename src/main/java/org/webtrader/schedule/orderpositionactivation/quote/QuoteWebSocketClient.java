package org.webtrader.schedule.orderpositionactivation.quote;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.model.Quote;

import java.net.URI;

public class QuoteWebSocketClient extends WebSocketClient
{
    private final QuoteHandler handler;

    public QuoteWebSocketClient( URI serverUri, QuoteHandler handler )
    {
        super( serverUri );
        this.handler = handler;
    }

    @Override
    public void onOpen( ServerHandshake handshake )
    {
        Log.write( "WebSocket connected to: " + getURI() );
    }

    @Override
    public void onMessage( String message )
    {
        try
        {
            Quote quote = parseQuoteFromMessage( message );
            if ( quote != null )
            {
                handler.handle( quote );
            }
        } catch ( Exception e )
        {
            Log.write( "Failed to parse or handle quote: " + e.getMessage() );
        }
    }

    private Quote parseQuoteFromMessage( String message )
    {
        JSONObject json = new JSONObject( message );

        String symbol = json.optString( "symbol" );
        double bid = json.optDouble( "bid_price" );
        double ask = json.optDouble( "ask_price" );

        if ( symbol.isBlank() ) {
            return null;
        }

        return new Quote( symbol, bid, ask );
    }

    @Override
    public void onClose( int code, String reason, boolean remote )
    {
        Log.write( "QuoteWebSocketClient - WebSocket closed: " + reason );
    }

    @Override
    public void onError( Exception ex )
    {
        Log.write( "QuoteWebSocketClient - WebSocket error: " + ex.getMessage() );
    }
}
