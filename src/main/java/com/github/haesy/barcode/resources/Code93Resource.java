package com.github.haesy.barcode.resources;

import java.util.regex.Pattern;

import javax.ws.rs.Path;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.Code93Writer;

/**
 * Resource that generates images of Code 93 barcodes.
 * 
 * @see <a href= "https://en.wikipedia.org/wiki/Code_93">Code 93 - Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("code-93")
public class Code93Resource extends AbstractOneDimensionalResource
{
    private static final Code93Writer WRITER = new Code93Writer();
    // Full ASCII may lead to a longer encoded content, but this is catched in the
    // library.
    private static final Pattern PATTERN = Pattern.compile("\\p{ASCII}{1,80}");

    public Code93Resource()
    {
        super(WRITER, BarcodeFormat.CODE_93, "Code 93");
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
