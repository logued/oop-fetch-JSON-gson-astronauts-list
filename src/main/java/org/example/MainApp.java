package org.example;                // March 2024

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Demonstrates:
 * Use of Gson Deserializer class that allows us to parse JSON data
 * and populate a Java object of any structure.  Useful when JSON structure
 * does not map directly to a Java object.
 * - Use a HttpClient to get response from a remote API (Endpoint)
 * - ( i.e. http://api.open-notify.org/astros.json )
 * - get the JSON string (the response body from the response)
 *
 * Here, we convert the JSON string into an ArrayList of Java Astronaut objects using the GsonBuilder.
 * NOTE that the list of astronauts is embedded as a field called "people" in the JSON, so we have to
 * convert that into an ArrayList of Astronaut objects stored in the 'crew' field of
 * the Spacecraft class.
 *
 * This sample uses the following API:
 * API Request:   http://api.open-notify.org/iss-now.json
 * <p>
 * Response from API request contains a nested array of objects (as at March 2024)
 {
    "message": "success",
    "people": [
                {
                    "name": "Jasmin Moghbeli",
                    "craft": "ISS"
                },
                {
                    "name": "Andreas Mogensen",
                    "craft": "ISS"
                }
            ],
    "number": 7
 }
 */

class MainApp {

    final String ASTRONAUTS_URL = "http://api.open-notify.org/astros.json";   // address of API Endpoint

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.start();
    }

    public void start() {
        String jsonString = fetchJsonFromAPI(ASTRONAUTS_URL);

        // Instantiate a GsonBuilder and register the TypeAdapter (to adapt types!)
        // passing in :
        // - the Spacecraft class definition.
        // - a deserializer object that we have written (containing the implemented deserialize() method)
        // - the deserializer contains the code we write to map the data from Json to a Java object.
        Gson gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Spacecraft.class,
                        new JsonDeserializerCrew())
                .serializeNulls()
                .create();

        // Now that we have set up the Adapter, we call the fromJson() method
        // to apply the Deserializer to parse the JSON string and create and
        // populate the Spacecraft object.
        Spacecraft spacecraft = gsonBuilder.fromJson( jsonString,
                        new TypeToken<Spacecraft>(){}.getType()
                );

        System.out.println(spacecraft);
    }


    /**
     * Using the supplied URL, perform a Http GET request to retrieve the JSON data
     * from the API Endpoint.
     * @param url
     * @return a Json String
     * @throws IOException
     */
    private String fetchJsonFromAPI(String url)
    {
        // Ref:  https://www.baeldung.com/java-9-http-client
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()  // build an HTTP request
                .uri(URI.create(url))
                .timeout(Duration.of(10,SECONDS))
                .GET()
                .build();

        HttpResponse<String> response = null;

        // client.send() throws a Checked Exceptions, so we need to provide a try-catch block
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Check the response code returned from the API endpoint
        // A code of 200 indicates success
        if (response.statusCode() != 200) {
            System.out.println("HTTP Request failed - Status code returned = " + response.statusCode());
            return null;
        }
        // get the body (the data payload) from the HTTP response object
        String jsonResponseString = response.body();

        if (jsonResponseString == null) {
            System.out.println("Json String was empty.");
            return null;
        }
        return jsonResponseString;
    }
}