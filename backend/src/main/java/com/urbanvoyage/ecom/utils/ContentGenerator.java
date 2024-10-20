package com.urbanvoyage.ecom.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ContentGenerator {

    private static final String BASE_URL = "http://localhost:3000/generate-content"; // Update with your base URL

    public static void main(String[] args) {
        String userId = "exampleUserId"; // Replace with actual user ID
        String fileName = "example.jpg"; // Replace with actual file name

        try {
            String response = fetchGeneratedContent(userId, fileName);
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fetchGeneratedContent(String userId, String fileName) throws Exception {
        String urlString = String.format("%s/%s/%s", BASE_URL, userId, fileName);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set up the request
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // Check response code
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        // Read the response
        StringBuilder responseBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
        }

        // Disconnect the connection
        connection.disconnect();

        return responseBuilder.toString(); // Return the response as a String
    }
}
