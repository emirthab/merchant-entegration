package utils.operations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class propertiesOperations {
    public static JSONObject getPropOBJ() throws IOException, JSONException, org.json.JSONException {
        String line;
        BufferedReader Br = new BufferedReader(new FileReader("properties.json"));
        StringBuilder Sb = new StringBuilder();
        while ((line = Br.readLine()) != null){
            Sb.append(line + '\n');
        }
        return new JSONObject(Sb.toString());
    }
    public static JSONObject AkinsoftPropConverter() throws IOException, org.json.JSONException {
        JSONObject akinsoft_prop = new JSONObject();
        JSONObject _obj = new JSONObject();
        String s_ip = "";
        String s_path = "";
        String s_user = "";
        String s_password = "";
        String prop_ip = null;
            akinsoft_prop = (JSONObject)getPropOBJ().get("akinsoft_properties");
            prop_ip = akinsoft_prop.getString("host");
        boolean stop1 = false;
        boolean stop2 = false;
        boolean SecondIsOpen = false;
        for(int i = 19; i < prop_ip.length(); i++) {
            char c = prop_ip.charAt(i);
            if (c == '/') {
                stop1=true;
            }
            if (c == '?'){
                stop2 = true;
            }
            if (!stop1) {
                s_ip += c;
            }else if(SecondIsOpen && !stop2){
                s_path += c;
            }else {
                SecondIsOpen = true;
            }
        }
            s_password = akinsoft_prop.getString("pass");
            s_user = akinsoft_prop.getString("user");
            _obj.put("s_ip",s_ip);
            _obj.put("s_path",s_path);
            _obj.put("s_user",s_user);
            _obj.put("s_password",s_password);
        return _obj;
    }
    public static void SaveAkinsoftProp(String ip,String path,String pass,String username) {
        JSONObject objmain = new JSONObject();
        try {
            objmain = getPropOBJ();
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        JSONObject WRTobj = new JSONObject();
        String sb_host = "jdbc:firebirdsql://"+ip+"/"+path+"?charSet=utf-8";
        try {
            WRTobj.put("host",sb_host);
            WRTobj.put("user", username);
            WRTobj.put("pass", pass);
            objmain.put("akinsoft_properties",WRTobj);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("properties.json");
            JsonElement jsonElement = new JsonParser().parse(String.valueOf(objmain));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(jsonElement));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void SaveTrendyolProperties(String supid,String api_key,String api_secret){
        JSONObject objmain = new JSONObject();
        try {
            objmain = propertiesOperations.getPropOBJ();
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        JSONObject WRTobj = new JSONObject();
        try {
            WRTobj.put("api_key",api_key);
            WRTobj.put("api_secret",api_secret);
            WRTobj.put("supplier_id",supid);
            objmain.put("trendyol_api_properties",WRTobj);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("properties.json");
            JsonElement jsonElement = new JsonParser().parse(String.valueOf(objmain));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(jsonElement));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void SaveHepsiburadaProperties(String merchant_id,String username,String password){
        JSONObject objmain = new JSONObject();
        try {
            objmain = propertiesOperations.getPropOBJ();
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        JSONObject WRTobj = new JSONObject();
        try {
            WRTobj.put("username",username);
            WRTobj.put("password",password);
            WRTobj.put("merchant_id",merchant_id);
            objmain.put("hepsiburada_api_properties",WRTobj);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("properties.json");
            JsonElement jsonElement = new JsonParser().parse(String.valueOf(objmain));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(jsonElement));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void SaveLastEntegDateLong(long dateLong){
        JSONObject objmain = new JSONObject();
        try {
            objmain = propertiesOperations.getPropOBJ();
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        try {
            objmain.put("lastdatetimelong",dateLong);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("properties.json");
            JsonElement jsonElement = new JsonParser().parse(String.valueOf(objmain));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(jsonElement));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
