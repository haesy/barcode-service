package com.github.haesy.barcode.resources;

import java.util.EnumMap;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.github.haesy.barcode.exceptions.ClientException;
import com.github.haesy.barcode.util.BitMatrixStreamingOutput;
import com.github.haesy.barcode.util.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.OneDimensionalCodeWriter;

/**
 * Base class for 1-D barcodes.
 * 
 * @author Yannick Häßler
 */
public abstract class AbstractOneDimensionalResource
{
    /** ZXing 1-D barcode writer */
    private final OneDimensionalCodeWriter writer;
    /** ZXing barcode format */
    private final BarcodeFormat format;
    /** displayed name of the barcode standard */
    private final String name;

    public AbstractOneDimensionalResource(OneDimensionalCodeWriter writer, BarcodeFormat format, String name)
    {
        this.writer = writer;
        this.format = format;
        this.name = name;
    }

    /**
     * Returns a response that contains an image of an 1-D barcode with the given
     * content.
     * 
     * @param content
     *            The content encoded in the barcode
     * @param width
     *            The preferred width in pixels
     * @param height
     *            The preferred height in pixels
     * @param margin
     *            The horizontal margin in pixels before and after the barcode
     * @param fgColor
     *            The color of the bars in hex format (RRGGBB or AARRGGBB)
     * @param bgColor
     *            The color of the background in hex format (RRGGBB or AARRGGBB)
     * @return The response that contains the image of the barcode
     */
    @GET
    public Response getResponse(@QueryParam("content") String content,
            @QueryParam("width") @DefaultValue("0") int width, @QueryParam("height") @DefaultValue("0") int height,
            @QueryParam("margin") Integer margin, @QueryParam("color") @DefaultValue("000000") Color fgColor,
            @QueryParam("bg-color") @DefaultValue("FFFFFF") Color bgColor)
    {
        validateContent(content);

        final Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        if (margin != null)
        {
            hints.put(EncodeHintType.MARGIN, margin);
        }

        final BitMatrix matrix = encode(content, width, height, hints);
        final MatrixToImageConfig config = new MatrixToImageConfig(fgColor.intValue(), bgColor.intValue());
        return new BitMatrixStreamingOutput(matrix, config).toResponseBuilder().build();
    }

    /**
     * Validates the given content. If it is invalid, a {@link ClientException} is
     * thrown.
     * 
     * @param content
     *            The content encoded in the barcode
     */
    protected void validateContent(String content)
    {
        if (content == null || content.length() == 0)
        {
            throw new ClientException("content must not be empty");
        }
    }

    protected BitMatrix encode(String content, int width, int height, Map<EncodeHintType, Object> hints)
    {
        try
        {
            return writer.encode(content, format, width, height, hints);
        }
        catch (IllegalArgumentException | NullPointerException ex)
        {
            throw new ClientException("failed to encode " + name + ": " + ex.getMessage(), ex);
        }
    }
}
