package org.example;

// Class that implements the JsonDeserializer interface defined in Gson library
// and implements the deserialize() method that will be used by the Gson parser to
// extract data from the JSON string, and put that data into the desired fields
// in the user defined Java Objects ( i.e. IssPositionAtTime object).
// This is used when our Java classes do NOT exactly match the structure and key names
// used in the JSON data. A mapping must be performed using code.
//
// This is where we MAP the structure of the JSON String onto the Java object.

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Create our own Deserializer class that implements the JsonDeserializer for
 * a specific class type (here, the Spacecraft  class).
 *
 * The deserialize() method will be called by the Gson parser when it needs to
 * parse a JSON String.  In this method, we 'get' the JSON as an JsonObject, and
 * we use appropriate JsonObject methods to extract the required fields.
 * We then instantiate a new Java object (ISSPositionAtTime) and populate it with
 * the data extracted from the JsonObject.
 *
 * JSON String --> JsonObject --> Java Object
 *
 */
public class JsonDeserializerSpacecraft implements JsonDeserializer<Spacecraft> {

    public Spacecraft deserialize(JsonElement json,
                                  Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {

        // get Json String as a JsonObject
        JsonObject jsonObject = json.getAsJsonObject();

        // The JSON String describes an object with three keys: "message", "people" and "number".
        // We will get the "message" and the "number" fields, but we won't use them here.

        String message = jsonObject.get("message").getAsString();
        int number = jsonObject.get("number").getAsInt();

        // The "people" key identifies a Json Array of objects. ( starting with a "[" ).
        // Therefore, we have to extract this into a JsonArray object (from Gson library)
        // and we will then be able to iterate through its elements.
        JsonArray jsonArray = jsonObject.get("people").getAsJsonArray();

        ArrayList<Astronaut> list = new ArrayList<>();

        // Iterate over the elements of the array, and extract data from each object
        // to construct an Astronaut object for each one, and add to list.
        for(JsonElement jsonElement : jsonArray) {      // for each element in the array
            // convert element to jsonObject
            JsonObject object = jsonElement.getAsJsonObject();

            String name = object.get("name").getAsString();
            String craft = object.get("craft").getAsString();
            Astronaut astronaut = new Astronaut( name, craft );
            list.add( astronaut );
        }

        // Create a new Spacecraft object with the list of astronauts
        Spacecraft spacecraft = new Spacecraft( "ISS",list  );      // create Spacecraft object

        return spacecraft;
    }
}