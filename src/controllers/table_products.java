package controllers;

import API.API_trendyol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.operations.logger;
import utils.operations.productsOperations;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class table_products {

    public static boolean getStatus() {
        return status;
    }
    public static void setStatus(boolean status) {
        table_products.status = status;
    }

    public static boolean status =false;
    private Integer id;
    private String barcode;
    private String sku;
    private String stock_name;


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getStock_name() {
        return stock_name;
    }
    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public table_products(Integer id,String barcode,String sku,String stock_name){
        super();
        this.id = id;
        this.barcode = barcode;
        this.sku = sku;
        this.stock_name = stock_name;
    }
    //BEGIN TRENDYOL
    static ObservableList<table_products> productsTrendyol = FXCollections.observableArrayList();
    static ObservableList<table_products> getProductsListTrendyol(){
        return productsTrendyol;
    }
    static void listingProductsTrendyol(String stat,String _barcode,String sku,String name) throws IOException, JSONException{
        productsTrendyol.clear();
        JSONArray ar_TrendyolProducts = API_trendyol.GETapprovedProducts();
        JSONArray _productsARRAY = new JSONArray();
        try{
            _productsARRAY = productsOperations.Products_array();
        }catch (org.json.JSONException e){
            System.out.println(logger.COLOR_PURPLE+"Products Listesi Boş"+logger.COLOR_RESET);
        }
        ArrayList<Integer> removeList = new ArrayList<>();
        status = true;
        for (int i =0;i<ar_TrendyolProducts.length() && status;i++) {
            JSONObject _obj = ar_TrendyolProducts.getJSONObject(i);
            String _barcode1 = _obj.getString("barcode");
            boolean _barcodeFound = false;
                for(int z=0;z<_productsARRAY.length() && status ;z++) {
                    JSONObject _productOBJ = _productsARRAY.getJSONObject(z);
                    String _barcodeZ = _productOBJ.getString("barcode");
                    boolean _TrendyolStat = _productOBJ.getBoolean("trendyol");
                    if(_TrendyolStat && _barcodeZ.equals(_barcode1)){
                        if (stat.equals("Pasif")) {
                            removeList.add(z);
                        }else{
                            _barcodeFound = true;
                        }
                    }
                    if(!_TrendyolStat && _barcodeZ.equals(_barcode1) && stat.equals("Aktif")){
                        _barcodeFound = true;
                        removeList.add(z);
                    }
                }
                if(stat.equals("Aktif") && !_barcodeFound){
                    removeList.add(i);
                }
        }
        for (int i =0;i<removeList.size();i++) {
            int removeIndex = removeList.get(i) - i;
            ar_TrendyolProducts.remove(removeIndex);
        }
        removeList.clear();
        if (!_barcode.equals("")){
            for(int i=0;i<ar_TrendyolProducts.length() && status ;i++){
                JSONObject _obj = ar_TrendyolProducts.getJSONObject(i);
                String barcode = _obj.getString("barcode");
                if(!barcode.equals(_barcode)){
                    removeList.add(i);
                }
            }
            for (int i =0;i<removeList.size();i++) {
                int removeIndex = removeList.get(i) - i;
                ar_TrendyolProducts.remove(removeIndex);
            }
            removeList.clear();
        }
        if(!sku.equals("")){
            for(int i=0;i<ar_TrendyolProducts.length() && status ;i++){
                JSONObject _obj = ar_TrendyolProducts.getJSONObject(i);
                String _sku = _obj.getString("sku");
                if (!_sku.equals(sku)){
                    removeList.add(i);
                }
            }
            for (int i =0;i<removeList.size();i++) {
                int removeIndex = removeList.get(i) - i;
                ar_TrendyolProducts.remove(removeIndex);
            }
            removeList.clear();
        }
        if(!name.equals("")){
            for(int i=0;i<ar_TrendyolProducts.length() && status ;i++){
                JSONObject _obj = ar_TrendyolProducts.getJSONObject(i);
                String _name = _obj.getString("name");
                //TODO büyük küçük harf duyarlılığı kaldırılacak
                if (!_name.contains(name)){
                    removeList.add(i);
                }
            }
            for (int i =0;i<removeList.size();i++) {
                int removeIndex = removeList.get(i) - i;
                ar_TrendyolProducts.remove(removeIndex);
            }
            removeList.clear();
        }
        for (int i =0;i<ar_TrendyolProducts.length();i++) {
            if (status) {
                JSONObject _obj = ar_TrendyolProducts.getJSONObject(i);
                productsTrendyol.add(new table_products(i + 1, _obj.getString("barcode"), _obj.getString("sku"), _obj.getString("name")));
            }
        }
        status = false;
    }
    //END TRENDYOL

    //BEGIN AKINSOFT
    static ObservableList<table_products> productsAkinsoft = FXCollections.observableArrayList();

    static ObservableList<table_products> getProductsListAkinsoft(){
        return productsAkinsoft;
    }
    static void listingProductsAkinsoft(String stat,String barcode,String sku,String name) throws SQLException, IOException, JSONException {
        productsAkinsoft.clear();
        JSONArray _productsARRAY = new JSONArray();
        status = true;
        int _id = 1;
        String where = "WHERE ";
        if(stat.equals("Pasif")) {
        try {
            _productsARRAY = productsOperations.Products_array();
            ArrayList<String> _arPassives = new ArrayList<>();
            for(int i=0;i<_productsARRAY.length();i++){
                JSONObject _obj = _productsARRAY.getJSONObject(i);
                String _barcode = _obj.getString("barcode");
                if(_obj.getBoolean("akinsoft")){
                    _arPassives.add(_barcode);
                }
            }
            if(_arPassives.size()!=1){
                for(int z=0;z<_arPassives.size();z++){
                    String _barcode = _arPassives.get(z);
                    if(z==0){
                        where += "(NOT BARKODU = '"+_barcode+"'";
                    }else if(z!=_arPassives.size()-1){
                        where += " AND NOT BARKODU = '"+_barcode+"'";
                    }else{
                        where += " AND NOT BARKODU = '"+_barcode+"')";
                    }
                }
            }else{
                String _barcode = _arPassives.get(0);
                where += "(NOT BARKODU = '"+_barcode+"')";
            }
        }catch (org.json.JSONException e){
            System.out.println(logger.COLOR_PURPLE+"Products Listesi Boş"+logger.COLOR_RESET);
        }
        }
        if(stat.equals("Aktif")) {
            try{
                _productsARRAY = productsOperations.Products_array();
                ArrayList<String> _arActives = new ArrayList<>();
                for(int i=0;i<_productsARRAY.length();i++){
                    JSONObject _obj = _productsARRAY.getJSONObject(i);
                    String _barcode = _obj.getString("barcode");
                    if(_obj.getBoolean("akinsoft")){
                        _arActives.add(_barcode);
                    }
                }
                if(_arActives.size()!=1){
                    for(int z=0;z<_arActives.size();z++){
                        String _barcode = _arActives.get(z);
                        if(z==0){
                            where += "(BARKODU = '"+_barcode+"'";
                        }else if(z!=_arActives.size()-1){
                            where += " OR BARKODU = '"+_barcode+"'";
                        }else{
                            where += " OR BARKODU = '"+_barcode+"')";
                        }
                    }
                }else{
                    String _barcode = _arActives.get(0);
                    where += "(BARKODU = '"+_barcode+"')";
                }
            }catch (JSONException e){
                System.out.println(logger.COLOR_PURPLE+"Products Listesi Boş"+logger.COLOR_RESET);
            }
            if(where.equals("WHERE ")){
                status=false;
            }
        }
        if(!barcode.equals("")){
            if(where.contains("(")){
                where += "AND (BARKODU = '"+barcode+"')";
            }else{
                where += "(BARKODU = '"+barcode+"')";
            }
        }
        if(!sku.equals("")){
            if(where.contains("(")){
                where += "AND (STOKKODU = '"+sku+"')";
            }else{
                where += "(STOKKODU = '"+sku+"')";
            }
        }
        if(!name.equals("")){
            if(where.contains("(")){
                where += "AND (STOK_ADI LIKE '%"+name+"%')";
            }else{
                where += "(STOK_ADI LIKE '%"+name+"%')";
            }
        }
        if(where.equals("WHERE ")){
            where = "";
        }
        //RESULT
        ResultSet res = SQL.Akinsoft_Query.STOKsorguForProductsTable(where);
        while(res.next() && status){
            productsAkinsoft.add(new table_products(_id,res.getString("BARKODU"),res.getString("STOKKODU"),res.getString("STOK_ADI")));
            _id ++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        status = false;
    }
    //END AKINSOFT




}
