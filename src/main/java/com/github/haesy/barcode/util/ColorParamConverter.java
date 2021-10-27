package com.github.haesy.barcode.util;

import java.util.regex.Pattern;

import javax.ws.rs.ext.ParamConverter;

import com.github.haesy.barcode.exceptions.ClientException;

/**
 * {@link ParamConverter} for {@link Color}.
 * 
 * @author Yannick Häßler
 */
public class ColorParamConverter implements ParamConverter<Color>
{
    private static final Pattern RGB = Pattern.compile("\\p{XDigit}{6}");
    private static final Pattern ARGB = Pattern.compile("\\p{XDigit}{8}");

    @Override
    public Color fromString(String value)
    {
        if (value == null)
        {
            return null;
        }
        else if (RGB.matcher(value).matches())
        {
            return new Color(Integer.parseInt(value, 16) | 0xFF_00_00_00);
        }
        else if (ARGB.matcher(value).matches())
        {
            return new Color(Integer.parseInt(value, 16));
        }
        else
        {
            throw new ClientException("color must have the format RRGGBB or AARRGGBB");
        }
    }

    @Override
    public String toString(Color value)
    {
        if (value == null)
        {
            return null;
        }
        return Integer.toHexString(value.intValue() & 0xFF_FF_FF_FF);
    }
}
