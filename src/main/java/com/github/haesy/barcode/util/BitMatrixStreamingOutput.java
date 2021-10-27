package com.github.haesy.barcode.util;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * {@link StreamingOutput} for {@link Response}s that contain a
 * {@link BitMatrix}.
 * 
 * @author Yannick Häßler
 */
public class BitMatrixStreamingOutput implements StreamingOutput
{
    private static final String CACHE_CONTROL_HEADER = "Cache-Control";
    private static final String CACHE_CONTROL_VALUE;

    static
    {
        final int maxAge = Math.toIntExact(Duration.ofDays(7).toSeconds());
        CACHE_CONTROL_VALUE = String.format("private, max-age=%d, immutable", maxAge);
    }

    private final BitMatrix matrix;
    private final MatrixToImageConfig config;

    public BitMatrixStreamingOutput(BitMatrix matrix, MatrixToImageConfig config)
    {
        this.matrix = matrix;
        this.config = config;
    }

    @Override
    public void write(OutputStream output) throws IOException
    {
        MatrixToImageWriter.writeToStream(matrix, "PNG", output, config);
    }

    public ResponseBuilder toResponseBuilder()
    {
        return Response.ok(this, "image/png").header(CACHE_CONTROL_HEADER, CACHE_CONTROL_VALUE);
    }
}
