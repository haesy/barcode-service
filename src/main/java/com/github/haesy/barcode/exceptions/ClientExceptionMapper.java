package com.github.haesy.barcode.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientException>
{
    @Override
    public Response toResponse(ClientException ex)
    {
        return ex.getResponse();
    }
}