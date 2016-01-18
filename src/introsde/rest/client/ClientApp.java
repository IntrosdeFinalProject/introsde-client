package introsde.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.*;


public class ClientApp {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Scanner input = new Scanner(System.in);
		String name = null;
		int operation = -1;

     
		System.out.println("Welcome to LIFECOACH APP!");
		System.out.println(" ");
		System.out.println("This application will help you to control your everyday physical activity and hydration. ");
		System.out.println("Measure the number of steps per day and how many litres of water you drink and see if it is enough for balanced healthy life. ");
		System.out.println("It is recommended to make at least 3000 steps every day and to drink at least 3 litres of water. These goals are defined by us by default. But you are welcome to change them.");


		System.out.println("\nPlease, choose a number of operation from the MENU below in order to proceed\n");


		while (operation != 0) {
			System.out.println("\nMENU'\n");
			System.out.println("1 - Print your current information");
			System.out.println("2 - Set the number of steps you've made today and check if the goal is hit");
			System.out.println("3 - Set the number of litres of water that you've drunk today and check if the goal is hit");
			System.out.println("4 - Set the goal - number of steps you should make, at least 3000 recommended");
			System.out.println("5 - Set the goal - how many litres of water you should drink, at least 3 litres recommended");
			System.out.println("6 - Print all the current goals ");
			System.out.println("0 - Exit");

			System.out.print("\nHow do you want to proceed?\n");
			operation = Integer.parseInt(input.nextLine());

			if (operation < 0 || operation > 6) {
				System.out.print("\nOperation not allowed, try again!\n\n");
			}

			switch (operation) {
				case 0:
					System.out.println("\nThank you for using LIFECOACH APP! Hope to see you soon!");
					break;
				case 1:
					 String ENDPOINT = "https://limitless-chamber-1231.herokuapp.com/introsde/businessLogic/getPersonDetails";

					 DefaultHttpClient client = new DefaultHttpClient();
					 HttpGet request = new HttpGet(ENDPOINT);
					 HttpResponse response = client.execute(request);

					 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

					 StringBuffer result = new StringBuffer();
					 String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}

					JSONObject o = new JSONObject(result.toString());

					if (response.getStatusLine().getStatusCode() == 200) {

						System.out.println("***********************************************");
						System.out.println("USER'S INFORMATION");
						System.out.println("***********************************************");

						System.out.println("Name: " + o.getString("name"));
						System.out.println("Surname: " + o.getString("lastname"));
						System.out.println("id: " + o.getInt("idPerson"));
						System.out.println("Birthdate: " + o.getString("birthdate"));

						 System.out.println("***********************************************");
						 System.out.println("USER'S CURRENT HEALTH PROFILE");
						 System.out.println("***********************************************");

						 for(int i = 0; i < o.getJSONArray("lifeStatus").length(); i++){
						 	System.out.println("Measure: "+o.getJSONArray("lifeStatus").getJSONObject(i).getString("measure"));
						 	System.out.println("Value: "+o.getJSONArray("lifeStatus").getJSONObject(i).get("value"));
						 System.out.println();
					}

			}
			 break;
   				
   				case 2:
			  			int value = -1;
			  			while(value < 0){
			  				System.out.println("Health profile - steps: ");
			  				System.out.println("Insert new value: ");
			  				value = Integer.parseInt(input.nextLine());
			  				if(value < 0){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Edit measure STEPS in Life Status
			        ENDPOINT = "https://damp-sea-9180.herokuapp.com/introsde/processCentric/updateHP";
			       ClientConfig clientConfig9 = new ClientConfig();
			 	   Client client9 = ClientBuilder.newClient(clientConfig9);
                    client = new DefaultHttpClient();
        
  
                   HttpGet request9 = new HttpGet(ENDPOINT);
                   HttpResponse response9 = client.execute(request9);

			 		WebTarget service9 = client9.target(ENDPOINT);

			    	Response res9 = null;
			 		String putResp9 = null;

			    	String updateLifeStatus9 ="{" + "\"value\": " + value + "," + "\"measureName\": \"" + "steps" + "\"" + "}";

			    	res9 = service9.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateLifeStatus9));
			    	putResp9 = res9.readEntity(String.class);

			    	if(res9.getStatus() != 200 ){
			    		System.out.println("ERROR during updating! Please, try again!");
			    	}else{

			  	    	o = new JSONObject(putResp9.toString());


			  	    		System.out.println("steps - updated life status measure => "+o.getJSONObject("comparisonInfo").getInt("lifeStatusValue"));
			  	    		System.out.println("steps - goal set => "+o.getJSONObject("comparisonInfo").getInt("goalValue"));

			  	    		String resp = o.getJSONObject("comparisonInfo").getString("result");
			  	    		if(resp.equals("ok")){
			  	    			System.out.println("\nCONGRATULATIONS! YOU HAVE REACHED THE GOAL!!!");
			  	    			String picture_url = o.getJSONObject("resultInfo").getString("picture_url");
			  	    			System.out.println("Open the link to see the motivational picture for your future success: "+picture_url);
			  	    		}
			  	    		else{
			  	    			System.out.println("\nUNFORTUNATELY YOU HAVEN'T REACHED THE GOAL!!!");
			  	    			System.out.println("You should try harder! This is a motivational phrase for you: "+o.getJSONObject("resultInfo").getString("quote"));
			  	    		}
			  	    	}

            break;
			  		case 3:
			  			int value2 = -1;
			  			while(value2 < 0){
			  				System.out.println("Health profile - water: ");
			  				System.out.println("Insert new value: ");
			  				value2 = Integer.parseInt(input.nextLine());
			  				if(value2 < 0){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Edit measure WATER in Life Status
			        ENDPOINT = "https://damp-sea-9180.herokuapp.com/introsde/processCentric/updateHP";
			       ClientConfig clientConfig1 = new ClientConfig();
			 	   Client client1 = ClientBuilder.newClient(clientConfig1);
                    client = new DefaultHttpClient();
        
  
                   HttpGet request1 = new HttpGet(ENDPOINT);
                   HttpResponse response1 = client.execute(request1);

			 		WebTarget service1 = client1.target(ENDPOINT);

			    	Response res1 = null;
			 		String putResp1 = null;

			    	String updateLifeStatus1 ="{" + "\"value\": " + value2 + "," + "\"measureName\": \"" + "water" + "\"" + "}";

			    	res1 = service1.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateLifeStatus1));
			    	putResp1 = res1.readEntity(String.class);

			    	if(res1.getStatus() != 200 ){
			    		System.out.println("ERROR during updating! Please, try again!");
			    	}else{

			  	    	o = new JSONObject(putResp1.toString());

			  	    
			  	    		System.out.println("water - updated life status measure => "+o.getJSONObject("comparisonInfo").getInt("lifeStatusValue"));
			  	    		System.out.println("water - goal set => "+o.getJSONObject("comparisonInfo").getInt("goalValue"));

			  	    		String resp = o.getJSONObject("comparisonInfo").getString("result");
			  	    		if(resp.equals("ok")){
			  	    			System.out.println("\nCONGRATULATIONS! YOU HAVE REACHED THE GOAL!!!");
			  	    			String picture_url = o.getJSONObject("resultInfo").getString("picture_url");
			  	    			System.out.println("Open the link to see the motivational picture for your future success: "+picture_url);
			  	    		}
			  	    		else{
			  	    			System.out.println("\nUNFORTUNATELY YOU HAVEN'T REACHED THE GOAL!!!");
			  	    			System.out.println("You should try harder! This is a motivational phrase for you: "+o.getJSONObject("resultInfo").getString("quote"));
			  	    		}
			  	    	}

            break;

			  		case 4:
			  			int value3 = -1;
			  			while(value3 < 0 || value3 > 1000000){
			  				System.out.println("Goal - steps: ");
			  				System.out.println("Insert new value: ");
			  				value3 = Integer.parseInt(input.nextLine());
			  				if(value3 < 0 || value3 > 1000000){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Change the goal STEPS
			    	ENDPOINT = "https://damp-sea-9180.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig clientConfig4 = new ClientConfig();
			 	Client client4 = ClientBuilder.newClient(clientConfig4);

			 	WebTarget service4 = client4.target(ENDPOINT);

			    	Response res4 = null;
			 	String putResp4 = null;

			    	String updateGoal ="{" + "\"value\": " + value3 + "," + "\"type\": \"" + "steps" + "\"" + ","
                            + "\"idGoal\": \"" + "1" + "\""
                            + "}";


			    	res4 = service4.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal));
			    	putResp4 = res4.readEntity(String.class);

			    	if(res4.getStatus() != 200 ){
			    		System.out.println("ERROR while updating! Please, try again!");
			    	}else{
			    		System.out.println("Goal updated successfully!");
			    	}

			 break;

			  		case 5:
			  			int value4 = -1;
			  			while(value4 < 0){
			  				System.out.println("Goal - water: ");
			  				System.out.println("Insert new value: ");
			  				value4 = Integer.parseInt(input.nextLine());
			  				if(value4 < 0 || value4 > 10000){
			  					System.out.println("Value not allowed! Please, try again!");
			  				}
			  			}

			 	//Change the goal WATER
			    	ENDPOINT = "https://damp-sea-9180.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig clientConfig5 = new ClientConfig();
			 	Client client5 = ClientBuilder.newClient(clientConfig5);

			 	WebTarget service5 = client5.target(ENDPOINT);

			    	Response res5 = null;
			 	String putResp5 = null;

			    	String updateGoal5 ="{" + "\"value\": " + value4 + "," + "\"type\": \"" + "water" + "\"" + ","
                            + "\"idGoal\": \"" + "2" + "\""
                            + "}";

			    	res5 = service5.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal5));
			    	putResp5 = res5.readEntity(String.class);

			    	if(res5.getStatus() != 200){
			    		System.out.println("ERROR while updating! Please, try again!");
			    	}else{
			    		System.out.println("Goal updated successfully!");
			    	}
			 break;

                case 6:
                    ENDPOINT = "https://limitless-chamber-1231.herokuapp.com/introsde/businessLogic/getGoals";

                    DefaultHttpClient client6 = new DefaultHttpClient();
                    HttpGet request6 = new HttpGet(ENDPOINT);
                    HttpResponse response6 = client6.execute(request6);

                    BufferedReader rd6 = new BufferedReader(new InputStreamReader(response6.getEntity().getContent()));

                    StringBuffer result6 = new StringBuffer();
                    String line6 = "";
                    while ((line6 = rd6.readLine()) != null) {
                        result6.append(line6);
                    }

                    JSONArray o6 = new JSONArray(result6.toString());

                    if (response6.getStatusLine().getStatusCode() == 200) {

                        System.out.println("***********************************************");
                        System.out.println("USER'S GOALS");
                        System.out.println("***********************************************");

                        for(int i = 0; i < o6.length(); i++){
                            System.out.println("Goal: "+o6.getJSONObject(i).getString("type"));
                            System.out.println("Value: "+o6.getJSONObject(i).getInt("value"));
                            System.out.println("idGoal: "+o6.getJSONObject(i).getInt("idGoal"));
                            System.out.println("");
                        }

                    }
                    break;
			     		}
			    	}
			 }
		}

