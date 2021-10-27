package com.github.haesy.barcode.exceptions;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExeptionMapper implements ExceptionMapper<NotFoundException>
{
    @Override
    public Response toResponse(NotFoundException exception)
    {
        return Response.status(Status.NOT_FOUND).entity("Error 404 - Not Found").type(MediaType.TEXT_PLAIN).build();
    }
}