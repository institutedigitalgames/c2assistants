package com.idg.idgweb;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import javax.ws.rs.core.UriBuilder;

/**
 * Hello world!
 *
 */
@SuppressWarnings("restriction")
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        //System.out.println("Initializing IDG Webservice HTTP Server...");
        HttpServer myServer = createHttpServer();
        myServer.start();
        
        //System.out.println(String.format("\nJersey Application Server started with WADL available at " + "%sapplication.wadl\n", getURI()));
        //System.out.println("Started IDG Embedded Jersey HTTP Server Successfully !!!");
    }
    
    
    private static HttpServer createHttpServer() throws IOException {
        ResourceConfig myResourceConfig = new PackagesResourceConfig("com.idg.idgwebservice");
        
        return HttpServerFactory.create(getURI(), myResourceConfig);
    }
    
    
    private static URI getURI(){
        return UriBuilder.fromUri("http://" + getHostName() + "/").port(8085).build();
    }
    
    private static String getHostName(){
        String hostName = "localhost";
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch(UnknownHostException e){
            e.printStackTrace();
        }
        return hostName;
    }
}
