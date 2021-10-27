package com.github.haesy.barcode.resources;

import java.util.regex.Pattern;

import javax.ws.rs.Path;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.Code128Writer;

/**
 * Resource that generates images of Code-128 barcodes.
 * 
 * @see <a href= "https://en.wikipedia.org/wiki/Code_128">Code 128 - Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("code-128")
public class Code128Resource extends AbstractOneDimensionalResource
{
    private static final Code128Writer WRITER = new Code128Writer();
    private static final Pattern PATTERN = Pattern.compile("\\p{ASCII}{1,80}");

    public Code128Resource()
    {
        super(WRITER, BarcodeFormat.CODE_128, "Code-128");
    }

    @Override
    protected void validateContent(String content)
    {
        super.validateContent(content);
        if (!PATTERN.matcher(content).matches())
        {
            throw new ClientException("content must only contain between 1 to 80 ASCII characters");
        }
    }
}
