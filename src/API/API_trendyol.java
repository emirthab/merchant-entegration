package API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.operations.propertiesOperations;

import java.io.IOException;
import java.net.URL;

public class API_trendyol {
    public static methods API = new methods();
    private static int totalpage = 1;

    public static void GETORDERcreated() throws IOException, JSONException {
        JSONObject trendyol_api_prop = (JSONObject)propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        String supplierid = trendyol_api_prop.getString("supplier_id");
        String api_secret = trendyol_api_prop.getString("api_secret");
        String api_key = trendyol_api_prop.getString("api_key");
        URL url = new URL("https://api.trendyol.com/sapigw/suppliers/"+supplierid+"/orders?status=Created");
        String result = API.GET(url,api_key,api_secret);
        JSONObject orders = new JSONObject(result);
        controllers.main_controller.setOrder_trendyol(orders.getInt("totalElements"));
    }
    public static String GETORDERexcludingCancelled(long dateBegin, long dateEnd) throws IOException, JSONException {
        JSONObject trendyol_api_prop = (JSONObject)propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        String supplierid = trendyol_api_prop.getString("supplier_id");
        String api_secret = trendyol_api_prop.getString("api_secret");
        String api_key = trendyol_api_prop.getString("api_key");
        String s_url = "https://api.trendyol.com/sapigw/suppliers/"+supplierid+"/orders?status=Created,Awaiting,Shipped,Picking,Invoiced,Delivered&size=200";
        if (dateBegin!= 0){
            s_url +="&startDate="+dateBegin;
        }
        if(dateEnd!= 0){
            s_url += "&endDate="+dateEnd;
        }
        URL url = new URL(s_url);
        return API.GET(url,api_key,api_secret);
    }
    public static String GETORDERcancelled() throws IOException, JSONException {
        JSONObject trendyol_api_prop = (JSONObject)propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        String supplierid = trendyol_api_prop.getString("supplier_id");
        String api_secret = trendyol_api_prop.getString("api_secret");
        String api_key = trendyol_api_prop.getString("api_key");
        String s_url = "https://api.trendyol.com/sapigw/suppliers/"+supplierid+"/orders?status=Cancelled&size=200";
        URL url = new URL(s_url);
        return API.GET(url,api_key,api_secret);
    }
    public static String itemUPDATEJSONgenerator(String barcode,int quantity,double saleprice,double listprice){
        String _s =                 "    {\n" +
                "      \"barcode\": \""+barcode+"\",\n" +
                "      \"quantity\": "+quantity+",\n" +
                "      \"salePrice\": "+saleprice+",\n" +
                "      \"listPrice\": "+listprice+"\n" +
                "    },\n"
                ;
        return _s;
    }
    public static void PriceAndInventoryUpdate(String items) throws IOException, JSONException {
        JSONObject trendyol_api_prop = (JSONObject)propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        String supplierid = trendyol_api_prop.getString("supplier_id");
        String api_secret = trendyol_api_prop.getString("api_secret");
        String api_key = trendyol_api_prop.getString("api_key");
        final String POST_PARAMS = "{\n" +
                "  \"items\": [\n" +
                    items+
                "  ]\n" +
                "}";
        System.out.println(POST_PARAMS);
        URL url = new URL("https://api.trendyol.com/sapigw/suppliers/"+supplierid+"/products/price-and-inventory");
        API.POST(url,api_key,api_secret,POST_PARAMS);
    }
    public static JSONArray GETapprovedProducts() throws IOException, JSONException {
        JSONArray products = new JSONArray();
        JSONObject trendyol_api_prop = (JSONObject)propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        String supplierid = trendyol_api_prop.getString("supplier_id");
        String api_secret = trendyol_api_prop.getString("api_secret");
        String api_key = trendyol_api_prop.getString("api_key");
        for(int z=0;z<20;z++) {
            if (z < totalpage) {
                URL url = new URL("https://api.trendyol.com/sapigw/suppliers/" + supplierid + "/products?approved=true&size=200&page=" + z);
                JSONObject mainobj = new JSONObject(API.GET(url, api_key, api_secret));
                totalpage = mainobj.getInt("totalPages");
                JSONArray productsAR = mainobj.getJSONArray("content");
                for (int i = 0; i < productsAR.length(); i++) {
                    JSONObject _obj = (JSONObject) productsAR.get(i);
                    JSONObject n_obj = new JSONObject();
                    String barcode = _obj.getString("barcode");
                    long SalePrice = _obj.getLong("salePrice");
                    long listPrice = _obj.getLong("listPrice");
                    int quantity = _obj.getInt("quantity");
                    String stockCode = _obj.getString("stockCode");
                    String title = _obj.getString("title");
                    n_obj.put("barcode", barcode);
                    n_obj.put("SalePrice", SalePrice);
                    n_obj.put("listPrice", listPrice);
                    n_obj.put("quantity", quantity);
                    n_obj.put("sku",stockCode);
                    n_obj.put("name",title);
                    products.put(n_obj);
                }
            }
        }
        totalpage= 1;
        return products;
    }
    public static boolean isBarcodeAvailable(String barcode){
        boolean bol = false;
        try {
            JSONObject product = GETproductFromBarcode(barcode);
            if (product.getString("barcode").equals(barcode)){
                bol = true;
            }
        } catch (IOException | JSONException e) {
            bol = false;
        }
        return bol;
    }
    public static JSONObject GETproductFromBarcode(String barcode) throws IOException, JSONException {
        JSONObject trendyol_api_prop = (JSONObject) propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        String supplierid = trendyol_api_prop.getString("supplier_id");
        String api_secret = trendyol_api_prop.getString("api_secret");
        String api_key = trendyol_api_prop.getString("api_key");
        URL url = new URL("https://api.trendyol.com/sapigw/suppliers/" + supplierid + "/products?approved=true&barcode=" + barcode);
        JSONObject mainobj = new JSONObject(API.GET(url, api_key, api_secret));
        JSONArray productsAR = mainobj.getJSONArray("content");
        return (JSONObject) productsAR.get(0);
    }
    public static JSONObject GETproductFromSKU(String sku) throws IOException, JSONException {
        JSONObject rtnOBJ = new JSONObject();
        JSONArray allProductsAR = GETapprovedProducts();
        for(int i =0; i<allProductsAR.length();i++){
            JSONObject _obj = allProductsAR.getJSONObject(i);
            String _sku = _obj.getString("sku");
            if(_sku.equals(sku)){
                rtnOBJ = _obj;
            }
        }
        return  rtnOBJ;
    }
}
