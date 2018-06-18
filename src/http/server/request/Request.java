/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server.request;

import http.server.Method;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Andrii_Rodionov
 */
public interface Request {

    String getURI();

    Method getMethod();

    String getParameter(String name);

    String getParameterOrNull(String name);

    Set<String> getParameterNames();

    Collection<String> getParameterValues();

    String getRequestAsText();
}
