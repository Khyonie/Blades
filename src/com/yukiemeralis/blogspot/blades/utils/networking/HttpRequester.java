package com.yukiemeralis.blogspot.blades.utils.networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequester 
{

    //
    // For the love of god don't use this unless you KNOW WHAT THE FUCK YOU ARE DOING
    // Makes requests from mojang's profile API
    //


    /**
     * Make a request to get skin data from a UUID
     * @param uuid
     * @return
     */
    public static HttpParser makeSkinRequest(String uuid)
    {
        HttpParser parser = new HttpParser();

        ArrayList<String> processed_lines = new ArrayList<>();

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false")).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) 
            {
                ArrayList<String> lines = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reader.lines().forEach(lines::add);

                lines.forEach(line -> {
                    processed_lines.add(processLine(line));
                });

                processed_lines.forEach(line -> {
                    if (line.split(":").length == 2)
                        parser.data.put(line.split(":")[0], line.split(":")[1]);
                });
            }
        } catch (Exception error) {
            System.out.println("[ WARN ] Failed to retrieve data for UUID " + uuid);
        }

        return parser;
    }

    public static HttpParser makeUUIDRequest(String username)
    {
        HttpParser parser = new HttpParser();

        ArrayList<String> processed_lines = new ArrayList<>();

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://api.mojang.com/users/profiles/minecraft/" + username)).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) 
            {
                ArrayList<String> lines = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reader.lines().forEach(lines::add);

                if (lines.size() == 1)
                {
                    String buffer = lines.get(0);
                    lines.clear();

                    for (String b : buffer.split(","))
                        lines.add(b);
                }

                lines.forEach(line -> {
                    processed_lines.add(processLine(line));
                });

                processed_lines.forEach(line -> {
                    if (line.split(":").length == 2)
                        parser.data.put(line.split(":")[0], line.split(":")[1]);
                });
            }
        } catch (Exception error) {
            System.out.println("[ WARN ] Failed to retrieve UUID for name " + username);
        }

        return parser;
    }

    private static String processLine(String input)
    {
        String buffer = input;

        // Remove whitespace
        buffer = buffer.trim().replace(" ", "");
        
        // Remove extraneous characters
        buffer = buffer.replace("{", "").replace("}", "").replace("[", "").replace("]", "");
        buffer = buffer.replace("\"", "").replace(",", "");

        return buffer;
    }
}
