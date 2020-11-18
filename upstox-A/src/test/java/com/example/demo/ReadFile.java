package com.example.demo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.util.EnumSet;

public class ReadFile {
    private long startOffset;
    private long endOffset;
    private SeekableByteChannel sbc;

    private final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public void process() throws IOException
    {
        startOffset = 0;
        sbc = null;
//                Files.newByteChannel("", EnumSet.of("UTF".));
        byte[] message = null;
        while((message = readRecord()) != null)
        {
            // do something
        }
    }

    public byte[] readRecord() throws IOException
    {
        endOffset = startOffset;

        boolean eol = false;
        boolean carryOver = false;
        byte[] record = null;

        while(!eol)
        {
            byte data;
            buffer.clear();
            final int bytesRead = sbc.read(buffer);

            if(bytesRead == -1)
            {
                return null;
            }

            buffer.flip();

            for(int i = 0; i < bytesRead && !eol; i++)
            {
                data = buffer.get();
                if(data == '\r' || data == '\n')
                {
                    eol = true;
                    endOffset += i;

                    if(carryOver)
                    {
                        final int messageSize = (int)(endOffset - startOffset);
                        sbc.position(startOffset);

                        final ByteBuffer tempBuffer = ByteBuffer.allocateDirect(messageSize);
                        sbc.read(tempBuffer);
                        tempBuffer.flip();

                        record = new byte[messageSize];
                        tempBuffer.get(record);
                    }
                    else
                    {
                        record = new byte[i];

                        // Need to move the buffer position back since the get moved it forward
                        buffer.position(0);
                        buffer.get(record, 0, i);
                    }

                    // Skip past the newline characters
//                    if(System.isWindowsOS())
//                    {
//                        startOffset = (endOffset + 2);
//                    }
//                    else
//                    {
//                        startOffset = (endOffset + 1);
//                    }

                    // Move the file position back
                    sbc.position(startOffset);
                }
            }

            if(!eol && sbc.position() == sbc.size())
            {
                // We have hit the end of the file, just take all the bytes
                record = new byte[bytesRead];
                eol = true;
                buffer.position(0);
                buffer.get(record, 0, bytesRead);
            }
            else if(!eol)
            {
                // The EOL marker wasn't found, continue the loop
                carryOver = true;
                endOffset += bytesRead;
            }
        }

        // System.out.println(new String(record));
        return record;
    }

}
