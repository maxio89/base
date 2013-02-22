package com.project.web;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public final class UrlHelper implements Serializable {
// -------------------------- STATIC METHODS --------------------------

    public static String encodeURL(ExternalContext externalContext, String file)
    {
        final StringBuilder append = new StringBuilder(externalContext.getRequestScheme()).append("://").append(externalContext.getRequestServerName());
        if (!isDefaultPort(externalContext)) {
            append.append(":").append(externalContext.getRequestServerPort());
        }
        final String encodedFile = ((HttpServletResponse) externalContext.getResponse()).encodeURL(file);
        if (file != null && !file.startsWith("/")) {
            append.append("/");
        }
        append.append(encodedFile);
        return append.toString();
    }

    private static boolean isDefaultPort(ExternalContext externalContext)
    {
        return ("http".equals(externalContext.getRequestScheme()) && 80 == externalContext.getRequestServerPort()) || (
            "https".equals(externalContext.getRequestScheme()) && 443 == externalContext.getRequestServerPort());
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private UrlHelper()
    {
    }
}