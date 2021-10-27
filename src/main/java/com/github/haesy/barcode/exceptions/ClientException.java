package com.github.haesy.barcode.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Exception that is thrown if the user did something wrong.
 * 
 * @author Yannick Häßler
 */
public class ClientException extends WebApplicationException
{
    private static final long serialVersionUID = 6298744591333681873L;

    public ClientException(String message)
    {
        this(message, null);
    }

    public ClientException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Override
    public Response getResponse()
    {
        return Response.status(Status.BAD_REQUEST).entity("Error 400 - Bad Request: " + getMessage())
                .type(MediaType.TEXT_PLAIN).build();
    }
}
