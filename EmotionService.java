package com.musicplayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.HttpURLConnection;

public class EmotionService {

    public static String getEmotion() {
        try {
            URI uri = new URI("http://localhost:5000/emotion");
            HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();

            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String res = response.toString();

            return res.split(":")[1].replaceAll("[\"}]", "");

        } catch (Exception e) {
            e.printStackTrace();
            return "neutral";
        }
    }
}