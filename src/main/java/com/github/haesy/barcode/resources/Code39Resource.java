package com.github.haesy.barcode.resources;

import java.util.regex.Pattern;

import javax.ws.rs.Path;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.Code39Writer;

/**
 * Resource that generates images of Code 39 barcodes.
 * 
 * @see <a href= "https://en.wikipedia.org/wiki/Code_39">Code 39 - Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("code-39")
public class Code39Resource extends AbstractOneDimensionalResource
{
    private static final Code39Writer WRITER = new Code39Writer();
    // Full ASCII may lead to a longer encoded content, but this is catched in the
    // library.
    private static final Pattern PATTERN = Pattern.compile("\\p{ASCII}{1,80}");

    public Code39Resource()
    {
        super(WRITER, BarcodeFormat.CODE_39, "Code 39");
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
