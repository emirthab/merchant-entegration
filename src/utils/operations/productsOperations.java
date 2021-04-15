package utils.operations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class productsOperations {
    public static void ProductsWriter(JSONArray addNewArray,JSONObject addNewObject) throws org.json.JSONException, IOException {
        String line;
        BufferedReader Br = new BufferedReader(new FileReader("products.json"));
        StringBuilder Sb = new StringBuilder();
        while ((line = Br.readLine()) != null){
            Sb.append(line + '\n');
        }
        JSONArray oldArray = new JSONArray();
        try {
            oldArray = new JSONArray(Sb.toString());
        } catch (org.json.JSONException e) {
            //not: arrayın "[" olması gerektiği söyleniyor yani boş durumu
            e.printStackTrace();
        }
        if (addNewArray!=null){
        for(int i = 0;i<addNewArray.length();i++){
            boolean status = false;
            JSONObject _obj2 = addNewArray.getJSONObject(i);
            String barcode = _obj2.getString("barcode");
            if (oldArray.toString().contains("barcode")){
            for(int z=0;z<oldArray.length();z++){   //tarama
                JSONObject _obj = oldArray.getJSONObject(z);
                String _barcode = _obj.getString("barcode");
                if(_barcode.equals(barcode) && !status){
                    oldArray.put(z,_obj2);
                    status = true;
                }
            }
            }
                if(!status){
                oldArray.put(_obj2);
            }
        }
        }
        if (addNewObject!=null){
            try{
                addNewObject.getBoolean("akinsoft");
            }catch (org.json.JSONException e){
                addNewObject.put("akinsoft",false);
            }
            try{
                addNewObject.getBoolean("trendyol");
            }catch (org.json.JSONException e){
                addNewObject.put("trendyol",false);
            }
            try{
                addNewObject.getBoolean("n11");
            }catch (org.json.JSONException e){
                addNewObject.put("n11",false);
            }
            try{
                addNewObject.getBoolean("hepsiburada");
            }catch (org.json.JSONException e){
                addNewObject.put("hepsiburada",false);
            }
            try{
                addNewObject.getBoolean("gittigidiyor");
            }catch (org.json.JSONException e){
                addNewObject.put("gittigidiyor",false);
            }
            try{
                addNewObject.getBoolean("woocommerce");
            }catch (org.json.JSONException e){
                addNewObject.put("woocommerce",false);
            }
            boolean status = false;
            String barcode = addNewObject.getString("barcode");
            if (oldArray.toString().contains("barcode")){
            for(int i=0;i<oldArray.length();i++){ //tarama
                JSONObject _obj = oldArray.getJSONObject(i);
                String _barcode = _obj.getString("barcode");
                if(_barcode.equals(barcode) && !status){
                    status = true;
                    oldArray.put(i,addNewObject);
                }
            }
            }
            if(!status) {
                oldArray.put(addNewObject);
            }
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("products.json");
            JsonElement jsonElement = new JsonParser().parse(String.valueOf(oldArray));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(jsonElement));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JSONArray Products_array() throws IOException, JSONException, org.json.JSONException {
        String line;
        BufferedReader Br = new BufferedReader(new FileReader("products.json"));
        StringBuilder Sb = new StringBuilder();
        while ((line = Br.readLine()) != null){
            Sb.append(line + '\n');
        }
            return new JSONArray(Sb.toString());
    }
    public static JSONObject getFromBarcode(String barcode) throws IOException, org.json.JSONException {
        JSONObject obj = new JSONObject();
        JSONArray Products_array = Products_array();
        for(int i=0;i<Products_array.length();i++){
            JSONObject _obj = Products_array.getJSONObject(i);
            String _barcode = _obj.getString("barcode");
            if(_barcode.equals(barcode)){
                obj = _obj;
            }
        }
        return obj;
    }
}
