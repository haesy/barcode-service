package com.github.haesy.barcode.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * {@link Provider} for all {@link ParamConverter}s that are used in this
 * service.
 * 
 * @author Yannick Häßler
 */
@Provider
public class BarcodeParamConverterProvider implements ParamConverterProvider
{
    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations)
    {
        if (rawType.equals(Color.class))
        {
            return (ParamConverter<T>) new ColorParamConverter();
        }
        else if (rawType.equals(ErrorCorrectionLevel.class))
        {
            return (ParamConverter<T>) new ErrorCorrectionLevelParamConverter();
        }
        return null;
    }
}
