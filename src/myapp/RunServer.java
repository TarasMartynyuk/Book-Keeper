/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp;

import http.server.HttpServer;
import myapp.books.*;
import myapp.servlets.ServletsMap;

import java.io.IOException;

/**
 *
 * @author Andrii_Rodionov
 */
public class RunServer {
     public static void main(String[] args) throws IOException {
        try {
            var servletsMap = new ServletsMap();
            var server = new HttpServer(servletsMap);
            server.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
