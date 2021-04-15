package utils.SYNC;

import API.API_trendyol;
import SQL.Akinsoft_Query;
import controllers.main_controller;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.operations.DateOperations;
import utils.operations.propertiesOperations;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class orderPool {
    //Trendyolda düşen Stokların Hareketi (gelen siparişe göre)
    public static void TrendyolOrdersCreated() throws IOException, JSONException {
        long dateBegin = propertiesOperations.getPropOBJ().getLong("lastdatetimelong");
        long dateEnd = main_controller.currenttimeLONG;
        JSONObject main_obj = new JSONObject(API_trendyol.GETORDERexcludingCancelled(dateBegin,dateEnd));
        JSONArray content = main_obj.getJSONArray("content"); //orders array
        for(int i=0;i< content.length();i++){
            JSONObject order = content.getJSONObject(i); //all informations
            JSONArray lines = order.getJSONArray("lines"); //products
            for(int z =0;z<lines.length();z++){
                JSONObject product = lines.getJSONObject(z);
                int quantity = product.getInt("quantity");
                String sku = product.getString("merchantSku");
                String barcode = product.getString("barcode");
                String orderNumber = order.getString("orderNumber");
                String orderDateLong = order.getString("orderDate");
                String orderFullName = order.getString("customerFirstName")+" "+order.getString("customerLastName");
            }
        }
    }
    //Trendyolda artan stokların hareketi (iptal edilen siparişe göre)
    public static JSONArray TrendyolOrdersCancelled() throws IOException, JSONException {
        JSONArray _ar = new JSONArray();
        long dateBegin = propertiesOperations.getPropOBJ().getLong("lastdatetimelong");
        long dateEnd = main_controller.currenttimeLONG;
        JSONObject main_obj = new JSONObject(API_trendyol.GETORDERcancelled());
        JSONArray content = main_obj.getJSONArray("content"); //orders array
        for(int i=0;i< content.length();i++) {
            String status="";
            long statCreatedDate=0;
            JSONObject order = content.getJSONObject(i); //all informations
            JSONArray lines = order.getJSONArray("lines"); //products
            JSONArray packageHistories = order.getJSONArray("packageHistories");
            for(int z =0;z<packageHistories.length();z++) {
                JSONObject _package = packageHistories.getJSONObject(z);
                String $status = _package.getString("status");
                long $statCreatedDate = _package.getLong("createdDate");
                if ($status.equals("Cancelled") && $statCreatedDate!=0 && $statCreatedDate>dateBegin && $statCreatedDate<dateEnd){
                    status = $status;
                    statCreatedDate = $statCreatedDate;
                }
            }
            if (status.equals("Cancelled")){
            for (int t =0;t< lines.length();t++){
                JSONObject product = lines.getJSONObject(t);
                product.put("sku",product.getString("merchantSku"));
                product.put("quantity",product.getInt("quantity"));
                product.put("barcode",product.getString("barcode"));
                product.put("orderNumber",order.getString("orderNumber"));
                product.put("cancelDateLong",statCreatedDate);
                product.put("orderFullName",order.getString("customerFirstName")+" "+order.getString("customerLastName"));
                _ar.put(product);
                }
            }
        }
        return _ar;
    }
    //Akınsoft muhasebe programında düşen stokların hareketi(Hızlı Satış veya Herhangi Hareket)
    public static JSONArray AkinsoftDropStock() throws IOException, JSONException, SQLException {
        String dateBegin = DateOperations.getLastEntegDate("yyyy-MM-dd hh:mm:ss");
        String dateEnd = DateOperations.longFormatter(main_controller.currenttimeLONG,"yyyy-MM-dd hh:mm:ss");
        ResultSet res = SQL.Akinsoft_Query.DateQueryStokHR(dateBegin,dateEnd,0);
        return Akinsoft_Query.getHRbasic(res);
    }
    //Akınsoft muhasebe programında artan stokların hareketi(İade veya herhangi bir stok girişi)
    public static JSONArray AkinsoftIncreaseStock() throws JSONException, SQLException, IOException {
        String dateBegin = DateOperations.getLastEntegDate("yyyy-MM-dd hh:mm:ss");
        String dateEnd = DateOperations.longFormatter(main_controller.currenttimeLONG,"yyyy-MM-dd hh:mm:ss");
        ResultSet res = SQL.Akinsoft_Query.DateQueryStokHR(dateBegin,dateEnd,1);
        return Akinsoft_Query.getHRbasic(res);
    }
    
}
