package com.github.haesy.barcode.util;

import javax.ws.rs.ext.ParamConverter;

import com.github.haesy.barcode.exceptions.ClientException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * {@link ParamConverter} for {@link ErrorCorrectionLevel} of QR-Codes.
 * 
 * @author Yannick Häßler
 */
public class ErrorCorrectionLevelParamConverter implements ParamConverter<ErrorCorrectionLevel>
{
    @Override
    public ErrorCorrectionLevel fromString(String value)
    {
        if (value == null)
        {
            return null;
        }
        return switch (value)
        {
        case "L" -> ErrorCorrectionLevel.L;
        case "M" -> ErrorCorrectionLevel.M;
        case "Q" -> ErrorCorrectionLevel.Q;
        case "H" -> ErrorCorrectionLevel.H;
        default -> throw new ClientException("error correction must be L, M, Q or H");
        };
    }

    @Override
    public String toString(ErrorCorrectionLevel value)
    {
        if (value == null)
        {
            return null;
        }
        return value.name();
    }
}
