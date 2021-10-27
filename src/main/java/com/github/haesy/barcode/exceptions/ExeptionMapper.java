package com.github.haesy.barcode.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExeptionMapper implements ExceptionMapper<Exception>
{
    @Override
    public Response toResponse(Exception exception)
    {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error 500 - Internal Server Error")
                .type(MediaType.TEXT_PLAIN).build();
    }
}