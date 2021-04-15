package API;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class API_gib {
    public static void deneme() throws IOException, JSONException {
        String api_secret = "zQEAyz1Xuc0lWVitfYqioslTSU8J0z";
        String api_key = "pPb0q8DLS5zxjaHfd0Z2";
        String token = "";
        URL obj = new URL("https://apitest.defterbeyan.gov.tr/auth/login");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        final String POST_PARAMS = "{\n" +
                "\"apiKey\":\"pPb0q8DLS5zxjaHfd0Z2\", " +
                "\"apiSecret\":\"zQEAyz1Xuc0lWVitfYqioslTSU8J0z\", " +
                "\"loginType\":\"ENTEGRATOR\"\n" +
                "}";
        System.out.println(POST_PARAMS);
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = postConnection.getResponseCode();
        System.out.println("POST Response Code :  " + responseCode);
        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
        if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST NOT WORKED");
        }

    }
}
