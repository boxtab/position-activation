package org.webtrader.schedule.orderpositionactivation.audit;

import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AuditFile implements AuditLogger
{
    private final String pathToStorage;

    private final AuditLogFormatter formatter;

    public AuditFile( String pathToStorage, AuditLogFormatter formatter )
    {
        this.pathToStorage = pathToStorage;
        this.formatter = formatter;
    }

    @Override
    public void saveAuditRecord(OrderAccountEntry entry )
    {
        String filePath = buildFilePath( entry );
        String content = formatter.format( entry );
        writeToFile( filePath, content );
    }

    private String buildFilePath( OrderAccountEntry entry )
    {
        return AuditPathUtil.buildFilePath( pathToStorage, entry );
    }

    private void writeToFile( String filePath, String content )
    {
        File file = new File( filePath );
        File parentDir = file.getParentFile();

        if ( parentDir != null && !parentDir.exists() )
        {
            if ( ! parentDir.mkdirs() )
            {
                Log.write( "Failed to create directories: " + parentDir.getAbsolutePath() );
                Log.write( "Check application permissions." );
                return;
            }
        }

        try ( BufferedWriter writer = new BufferedWriter( new FileWriter( file, true ) ) )
        {
            writer.write( content );
            writer.newLine();
        }
        catch ( IOException e )
        {
            Log.write( "Error writing to file: " + filePath + " | " + e.getMessage() );
        }
    }
}
