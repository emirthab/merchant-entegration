package API;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class methods {
    public void POST(URL url,String api_key,String api_secret,String POST_PARAMS) throws IOException {
        String encoded = Base64.getEncoder().encodeToString((api_key+":"+api_secret).getBytes(StandardCharsets.UTF_8));
        HttpURLConnection postConnection = (HttpURLConnection) url.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Authorization", "Basic "+encoded);
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
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
        } else {
            System.out.println("POST NOT WORKED");
        }
    }
    public String GET(URL url,String api_key,String api_secret) throws IOException, JSONException {
        String encoded = Base64.getEncoder().encodeToString((api_key+":"+api_secret).getBytes(StandardCharsets.UTF_8));
        String readLine = null;
        HttpURLConnection _connection = (HttpURLConnection) url.openConnection();
        _connection.setRequestMethod("GET");
        _connection.setRequestProperty("Authorization", "Basic "+encoded);
        int responseCode = _connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(_connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            return response.toString();
        }else{
            System.out.println("HATA OLUŞTU");
            return null;
        }
    }
}
