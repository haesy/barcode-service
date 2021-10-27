package com.github.haesy.barcode.resources;

import java.util.regex.Pattern;

import javax.ws.rs.Path;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.EAN8Writer;

/**
 * Resource that generates images of EAN-8 barcodes.
 * 
 * @see <a href= "https://en.wikipedia.org/wiki/EAN-8">EAN-8 - Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("ean-8")
public class EAN8Resource extends AbstractOneDimensionalResource
{
    private static final EAN8Writer WRITER = new EAN8Writer();
    private static final Pattern PATTERN = Pattern.compile("\\d{7}");

    public EAN8Resource()
    {
        super(WRITER, BarcodeFormat.EAN_8, "EAN-8");
    }

    @Override
    protected void validateContent(String content)
    {
        super.validateContent(content);
        if (!PATTERN.matcher(content).matches())
        {
            throw new ClientException("content must contain exactly 7 digits");
        }
    }
}
