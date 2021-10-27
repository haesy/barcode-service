package com.github.haesy.barcode.resources;

import java.util.regex.Pattern;

import javax.ws.rs.Path;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.EAN13Writer;

/**
 * Resource that generates images of EAN-13 barcodes.
 * 
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/International_Article_Number">EAN-13 -
 *      Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("ean-13")
public class EAN13Resource extends AbstractOneDimensionalResource
{
    private static final EAN13Writer WRITER = new EAN13Writer();
    private static final Pattern PATTERN = Pattern.compile("\\d{12}");

    public EAN13Resource()
    {
        super(WRITER, BarcodeFormat.EAN_13, "EAN-13");
    }

    @Override
    protected void validateContent(String content)
    {
        super.validateContent(content);
        if (!PATTERN.matcher(content).matches())
        {
            throw new ClientException("content must contain exactly 12 digits");
        }
    }
}
