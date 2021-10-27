package com.github.haesy.barcode.resources;

import java.util.regex.Pattern;

import javax.ws.rs.Path;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.ITFWriter;

/**
 * Resource that generates images of ITF barcodes. *
 * 
 * @see <a href= "https://en.wikipedia.org/wiki/Interleaved_2_of_5">ITF -
 *      Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("itf")
public class ITFResource extends AbstractOneDimensionalResource
{
    private static final ITFWriter WRITER = new ITFWriter();
    private static final Pattern PATTERN = Pattern.compile("(\\d\\d){1,40}");

    public ITFResource()
    {
        super(WRITER, BarcodeFormat.ITF, "ITF");
    }

    @Override
    protected void validateContent(String content)
    {
        super.validateContent(content);
        if (!PATTERN.matcher(content).matches())
        {
            throw new ClientException("content must contain only digits and have an even size between 2 and 80");
        }
    }
}
