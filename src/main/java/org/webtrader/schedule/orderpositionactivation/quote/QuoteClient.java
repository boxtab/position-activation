package org.webtrader.schedule.orderpositionactivation.quote;

import org.java_websocket.client.WebSocketClient;
import org.webtrader.schedule.orderpositionactivation.config.Config;
import org.webtrader.schedule.orderpositionactivation.log.Log;

import java.net.URI;
import java.net.URISyntaxException;

public class QuoteClient
{
    private final QuoteHandler handler;

    private WebSocketClient client;

    public QuoteClient( QuoteHandler handler )
    {
        this.handler = handler;
    }

    public void connectWithRetry()
    {
        new Thread( this::connectLoop, "QuoteClient-Reconnect-Thread" ).start();
    }

    private void connectLoop()
    {
        while ( true )
        {
            try {
                connect();
                while ( client != null && client.isOpen() ) {
                    Thread.sleep(1_000); // поддержка соединения
                }
            } catch ( Exception e ) {
                Log.write( "Quote client WebSocket error: " + e.getMessage() );
            }

            Log.write( "Reconnecting to WebSocket in 15s..." );
            try {
                Thread.sleep(15_000);
            } catch ( InterruptedException ignored ) {}
        }
    }

    private void connect()
    {
        try
        {
            URI uri = getQuotationInformerURI();

            client = new QuoteWebSocketClient( uri, handler );
            client.connectBlocking(); // <-- Важно: блокирует, пока не подключится
        }
        catch ( Exception e )
        {
            Log.write( "Failed to initialize WebSocketClient: " + e.getMessage() );
        }
    }

    private URI getQuotationInformerURI() throws URISyntaxException
    {
        String host = Config.getConfig().getProperty( "QUOTATION_INFORMER_HOST" );
        String port = Config.getConfig().getProperty( "QUOTATION_INFORMER_PORT" );

        return new URI( "ws://" + host + ":" + port );
    }
}
