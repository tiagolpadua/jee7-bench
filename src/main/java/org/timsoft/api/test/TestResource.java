package org.timsoft.api.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// WildFly
// http://localhost:8080/sample-jee7/api/test

// Weblogic
// http://localhost:7001/sample-jee7/api/test

@RequestScoped
@Path("test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
	@GET
	public Response test() {
		
		HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL("http://192.168.0.12:5000/");
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET"); 
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		    }
		    rd.close();
		    return Response.ok("Test: " + response.toString()).build();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		  
	}
}