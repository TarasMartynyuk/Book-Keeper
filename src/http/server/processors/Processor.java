/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server.processors;

import http.server.request.Request;
import http.server.response.Response;

/**
 *
 * @author andrii
 */
public interface Processor {

    void process(Request request, Response response);
    
}
