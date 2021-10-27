package com.github.haesy.barcode.util;

/**
 * Color for barcodes.
 * 
 * @author Yannick Häßler
 */
public record Color(int intValue)
{
    public static final Color BLACK = new Color(0xFF000000);
    public static final Color WHITE = new Color(0xFFFFFFFF);
}
