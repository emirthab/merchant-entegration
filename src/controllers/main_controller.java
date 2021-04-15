package controllers;

import API.API_trendyol;
import SQL.Akinsoft_Query;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.operations.DateOperations;
import utils.operations.logger;
import utils.operations.productsOperations;
import utils.operations.propertiesOperations;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class main_controller implements Initializable {
    public static long currenttimeLONG;
    @FXML
    //AKINSOFT SETTINGS
    public AnchorPane anchor_page_akinsoft_properties;
    public TextField akinsoft_settings_ip_adress;
    public TextField akinsoft_settings_file_path;
    public TextField akinsoft_settings_username;
    public TextField akinsoft_settings_password;
    public ImageView akinsoft_settings_back_icon;
    //SETTINGS PAGE
    public AnchorPane anchor_main_page;
    public ImageView settings_wolvox_logo;
    public ImageView settings_trendyol_logo;
    public ImageView settings_hepsiburada_logo;
    public ImageView settings_n11_logo;
    public ImageView settings_gittigidiyor_logo;
    public ImageView settings_woocommerce_logo;
    public Button button_save_last_enteg_time;
    public DatePicker last_enteg_date;
    public TextField last_enteg_hour;
    public AnchorPane anchor_page_hepsiburada_settings;

    public DatePicker getLast_enteg_date() {
        return last_enteg_date;
    }
    public TextField getLast_enteg_hour() {
        return last_enteg_hour;
    }
    //LOGS PAGE
    public TextArea logs_page_textarea;
    public DatePicker logs_page_datepicker;
    public Button logs_page_button_filter;
    public ChoiceBox logs_page_choicebox_1;
    //DEFAULT RIGHT PANE INFOS
    public int default_stock_akinsoft;
    public double default_listprice_akinsoft;
    public double default_saleprice_akinsoft;
    //PRODUCTS_PAGE
    public TabPane products_page_right_pane_tab_pane;
    public TextField products_page_quantity_input;
    public AnchorPane anchor_loading_pane;
    public AnchorPane anchor_products_page_right_pane_multiple_selection;
    public Button products_page_right_pane_disconnect_products;
    public CheckBox products_page_right_pane_check_trendyol_multi;
    public CheckBox products_page_right_pane_check_akinsoft_multi;
    public CheckBox products_page_right_pane_check_n11_multi;
    public CheckBox products_page_right_pane_check_gittigidiyor_multi;
    public CheckBox products_page_right_pane_check_hepsiburada_multi;
    public CheckBox products_page_right_pane_check_woocommerce_multi;
    public Button products_page_right_pane_connect_products;
    public ImageView products_page_right_pane_image;
    public TextField products_page_right_pane_1x1;
    public TextField products_page_right_pane_1x2;
    public TextField products_page_right_pane_1x3;
    public TextField products_page_right_pane_2x1;
    public TextField products_page_right_pane_2x2;
    public TextField products_page_right_pane_2x3;
    public TextField products_page_right_pane_3x1;
    public TextField products_page_right_pane_3x2;
    public TextField products_page_right_pane_3x3;
    public TextField products_page_right_pane_4x1;
    public TextField products_page_right_pane_4x2;
    public TextField products_page_right_pane_4x3;
    public TextField products_page_right_pane_5x1;
    public TextField products_page_right_pane_5x2;
    public TextField products_page_right_pane_5x3;
    public TextField products_page_right_pane_6x1;
    public TextField products_page_right_pane_6x2;
    public TextField products_page_right_pane_6x3;

    public String barcodeCurrentRightPane=null;
    public void setProducts_table(ObservableList<table_products> products1) {
        this.products_table.setItems(products1);
    }
    public Button products_page_right_pane_button_save;
    public CheckBox products_page_right_pane_check_akinsoft;
    public CheckBox products_page_right_pane_check_trendyol;
    public CheckBox products_page_right_pane_check_hepsiburada;
    public CheckBox products_page_right_pane_check_n11;
    public CheckBox products_page_right_pane_check_gittigidiyor;
    public CheckBox products_page_right_pane_check_woocommerce;
    public TextArea products_page_right_pane_product_infos;
    public TableView<table_products> products_table;
    public TableColumn<table_products,String> products_table_column_barcode;
    public TableColumn<table_products,String> products_table_column_sku;
    public TableColumn<table_products,String> products_table_column_stock_name;
    public ChoiceBox<String> products_page_active_status;
    public ChoiceBox<String> products_page_location_status;
    public TextField products_page_sku_input;
    public TextField products_page_product_name_input;
    public Button products_page_do_filter_button;
    public TextField products_page_barcode_input;
    public TableColumn<table_products, Integer> products_table_product_id;
    //TRENDYOL SETTINGS
    public AnchorPane anchor_page_trendyol_settings;
    public TextField trendyol_settings_supplier_id;
    public TextField trendyol_settings_api_key;
    public TextField trendyol_settings_api_secret;
    public ImageView trendyol_settings_back_icon;
    public static void setOrder_trendyol(int order_trendyol) {
        main_controller.order_trendyol = order_trendyol;
    }
    public static int order_trendyol =0;
    //HEPSIBURADA SETTINGS
    public TextField hepsiburada_settings_merchant_id;
    public TextField hepsiburada_settings_username;
    public TextField hepsiburada_settings_password;
    public ImageView hepsiburada_settings_back_icon;
    //CART NOTIFICATION
    public Text notification_carttext_trendyol_icon;
    public ImageView notification_cart_trendyol_icon;
    public ImageView notification_cart_hepsiburada_icon;
    public Text notification_carttext_hepsiburada_icon;
    public ImageView notification_cart_woocommerce_icon;
    public ImageView notification_cart_gittigidiyor_icon;
    public Text notification_carttext_woocommerce_icon;
    public Text notification_carttext_gittigidiyor_icon;
    public ImageView notification_cart_n11_icon;
    public Text notification_carttext_n11_icon;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        StartingUtils();
        //BEGIN PRODUCTS PAGE
        final ObservableList<TablePosition> selectedCells = products_table.getSelectionModel().getSelectedCells();
        selectedCells.addListener(new ListChangeListener<TablePosition>() {
            @Override
            public void onChanged(Change change) {
                if(selectedCells.size()>1 && !anchor_loading_pane.isVisible()){
                    anchor_products_page_right_pane_multiple_selection.setVisible(true);
                    products_page_right_pane_tab_pane.setVisible(false);
                }
                products_page_right_pane_disconnect_products.setOnMouseClicked((event) -> {
                    anchor_products_page_right_pane_multiple_selection.setVisible(false);
                    anchor_loading_pane.setVisible(true);
                    ArrayList<String> _ar = new ArrayList<>();
                    for (TablePosition pos : selectedCells) {
                        int row = pos.getRow();
                        Object val = products_table.getItems().get(row);
                        TableColumn col = products_table.getColumns().get(1);
                        String data = (String) col.getCellObservableValue(val).getValue();//data = barkod
                        _ar.add(data);
                    }
                    Thread threadDisconnectMultiply = new Thread() {
                        public void run() {
                            for (int i = 0; i < _ar.size(); i++) {
                                String barcode = _ar.get(i);
                                JSONObject _obj = new JSONObject();
                                try {
                                    if (products_page_right_pane_check_akinsoft_multi.isSelected()) {
                                            _obj.put("akinsoft", false);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (products_page_right_pane_check_trendyol_multi.isSelected()) {
                                            _obj.put("trendyol", false);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    _obj.put("barcode", barcode);
                                    productsOperations.ProductsWriter(null, _obj);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            anchor_loading_pane.setVisible(false);
                            this.stop();
                        }
                    };threadDisconnectMultiply.start();

                });
                products_page_right_pane_connect_products.setOnMouseClicked((event) -> {
                    anchor_products_page_right_pane_multiple_selection.setVisible(false);
                    anchor_loading_pane.setVisible(true);
                    ArrayList<String> _ar = new ArrayList<>();
                    for (TablePosition pos : selectedCells) {
                        int row = pos.getRow();
                        Object val = products_table.getItems().get(row);
                        TableColumn col = products_table.getColumns().get(1);
                        String data = (String) col.getCellObservableValue(val).getValue();//data = barkod
                        _ar.add(data);
                    }
                    Thread threadConnectMultiply = new Thread() {
                        public void run() {
                            for (int i = 0; i < _ar.size(); i++) {
                                String barcode = _ar.get(i);
                                JSONObject _obj = new JSONObject();
                                //TODO loglanacak
                                System.out.println(logger.COLOR_GREEN+barcode+logger.COLOR_RESET);
                                    try {
                                        if (products_page_right_pane_check_akinsoft_multi.isSelected()) {
                                            if (Akinsoft_Query.isBarcodeAvailable(barcode)) {
                                                _obj.put("akinsoft", true);
                                            } else {
                                                //todo loglanacak
                                                System.out.println(logger.COLOR_YELLOW+"barkod akınsoftta yok"+logger.COLOR_RESET);
                                            }
                                        }
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try{
                                        if (products_page_right_pane_check_trendyol_multi.isSelected()) {
                                            if(API_trendyol.isBarcodeAvailable(barcode)){
                                                _obj.put("trendyol", true);
                                            }else{
                                                //todo loglanacak
                                                System.out.println(logger.COLOR_YELLOW+"barkod trendyolda yok"+logger.COLOR_RESET);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        _obj.put("barcode", barcode);
                                        productsOperations.ProductsWriter(null, _obj);
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                            }
                            anchor_loading_pane.setVisible(false);
                            this.stop();
                        }
                    };threadConnectMultiply.start();
                });
                for (TablePosition pos : selectedCells) {
                    products_table.setOnMouseClicked((event) -> {
                        if (event.getClickCount() ==2 && !anchor_loading_pane.isVisible()){
                            products_page_right_pane_tab_pane.setVisible(true);
                            anchor_products_page_right_pane_multiple_selection.setVisible(false);
                            int row = pos.getRow();
                            Object val = products_table.getItems().get(row);
                            TableColumn col = products_table.getColumns().get(1);
                            String data = (String) col.getCellObservableValue(val).getValue();//data = barkod
                            barcodeCurrentRightPane = data;
                            String _strInfo = "Ürün Adı : "+products_table.getColumns().get(3).getCellObservableValue(products_table.getItems().get(pos.getRow())).getValue()+"\n"+
                                    "Barkodu : "+data+"\n"+
                                    "Stok Kodu : "+products_table.getColumns().get(2).getCellObservableValue(products_table.getItems().get(pos.getRow())).getValue()+"\n";
                            products_page_right_pane_product_infos.setText(_strInfo);
                            //begin image canvas
                            Image defaultImage = new Image("resources/img/icons/no-image-icon.png");
                            products_page_right_pane_image.setImage(defaultImage);
                            Thread ImageLazyLoad = new Thread() {
                                public void run() {
                                    String loc = products_page_location_status.getValue();
                                    if (loc.equals("Trendyol")) {
                                        try {
                                            String url = "";
                                            JSONObject _obj = API_trendyol.GETproductFromBarcode(data);
                                            JSONArray imagesAR = _obj.getJSONArray("images");
                                            JSONObject objImage = imagesAR.getJSONObject(0);
                                            String imageURL = objImage.getString("url");
                                            if (imageURL.startsWith("https://cdn.dsmcdn.com") && !imageURL.contains("_org_zoom.")) {
                                                if (imageURL.endsWith(".jpg") || imageURL.endsWith(".png")) {
                                                    url = imageURL.substring(0, imageURL.length() - 4) + "_org_zoom" + imageURL.substring(imageURL.length() - 4);
                                                } else if (imageURL.endsWith(".jpeg")) {
                                                    url = imageURL.substring(0, imageURL.length() - 5) + "_org_zoom" + imageURL.substring(imageURL.length() - 5);
                                                }
                                                Image img = new Image(url);
                                                products_page_right_pane_image.setImage(img);
                                            } else {
                                                Image img = new Image(imageURL);
                                                products_page_right_pane_image.setImage(img);
                                            }
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }this.stop();
                                }
                            };//end image canvas
                            ImageLazyLoad.start();
                            JSONObject productsJSON = null;
                            try {
                                productsJSON = productsOperations.getFromBarcode(data);
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                            resetFieldsProductsInfo();
                            try {
                                if(productsJSON.getBoolean("akinsoft")){
                                    products_page_right_pane_check_akinsoft.setSelected(true);
                                    int _stockAkinsoft = Akinsoft_Query.stokmiktar(data);
                                    products_page_right_pane_1x1.setText(String.valueOf(_stockAkinsoft));
                                    default_stock_akinsoft = _stockAkinsoft;
                                    JSONObject _akinsoftPrices = Akinsoft_Query.FIYATsorguTekil(data, null);
                                    products_page_right_pane_1x2.setText(String.valueOf(_akinsoftPrices.getDouble("alis")));
                                    products_page_right_pane_1x3.setText(String.valueOf(_akinsoftPrices.getDouble("satis")));
                                    default_listprice_akinsoft = _akinsoftPrices.getDouble("alis");
                                    default_saleprice_akinsoft = _akinsoftPrices.getDouble("satis");
                                }else{
                                    System.out.println(logger.COLOR_PURPLE+"Ürün Akınsoftla Eşleşmiyor"+logger.COLOR_RESET);
                                }
                            } catch (JSONException | IOException e) {
                                System.out.println(logger.COLOR_PURPLE+"Ürün Sisteme Tanımlı Değil"+logger.COLOR_RESET);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                if(productsJSON.getBoolean("trendyol")) {
                                    products_page_right_pane_check_trendyol.setSelected(true);
                                    JSONObject _trendyolInfos = API.API_trendyol.GETproductFromBarcode(data);
                                    products_page_right_pane_2x1.setText(String.valueOf(_trendyolInfos.getInt("quantity")));
                                    products_page_right_pane_2x2.setText(String.valueOf(_trendyolInfos.getDouble("listPrice")));
                                    products_page_right_pane_2x3.setText(String.valueOf(_trendyolInfos.getDouble("salePrice")));
                                }else{
                                    System.out.println(logger.COLOR_PURPLE+"Ürün Trendyolla Eşleşmiyor"+logger.COLOR_RESET);
                                }
                            } catch (JSONException | IOException e) {
                                System.out.println(logger.COLOR_PURPLE+"Ürün Sisteme Tanımlı Değil"+logger.COLOR_RESET);
                            }
                        }
                    });
                }
            };
        });
        //BEGIN SAVE RIGHTPANE
        products_page_right_pane_button_save.setOnMouseClicked((event) -> {
            JSONObject _obj = new JSONObject();
            if(barcodeCurrentRightPane != null) {
                    try {
                        if (products_page_right_pane_check_akinsoft.isSelected()) {
                            if(Akinsoft_Query.isBarcodeAvailable(barcodeCurrentRightPane)){
                            _obj.put("akinsoft", true);
                            if(default_stock_akinsoft!=Long.parseLong(products_page_right_pane_1x1.getText())){
                                //todo akinsoftta stok değiştir
                            }
                            if(default_listprice_akinsoft!=Long.parseLong(products_page_right_pane_1x2.getText())){
                                //todo akinsoftta alış fiyat değiştir
                            }
                            if(default_saleprice_akinsoft!=Long.parseLong(products_page_right_pane_1x3.getText())){
                                //todo akinsoftta satış fiyat değiştir
                            }
                            }else{
                                //todo barkod akınsoftta bulunamadı
                                _obj.put("akinsoft", false);
                            }
                        }else{
                            _obj.put("akinsoft", false);
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    //END AKINSOFT
                try {
                        if (products_page_right_pane_check_trendyol.isSelected()) {
                            if(API_trendyol.isBarcodeAvailable(barcodeCurrentRightPane)){
                            _obj.put("trendyol", true);
                            }else{
                                //todo barkod trendyolda bulunamadı
                                _obj.put("trendyol", false);
                            }
                        }else{
                            _obj.put("trendyol", false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //END TRENDYOL
                try {
                    _obj.put("barcode", barcodeCurrentRightPane);
                    productsOperations.ProductsWriter(null, _obj);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });//END SAVE RIGHTPANE
        //BEGIN FILTER
        products_page_do_filter_button.setOnMouseClicked((event) -> {
            String status = products_page_active_status.getValue();
            String loc = products_page_location_status.getValue();
                    Thread t1 = new Thread(){
                        public void run(){
                            try {
                                //important TABLO UZUNLUĞUYLA ALAKALI SORUN ÇÖZÜLECEK (bazen devamı gözükmüyor)
                                table_products.setStatus(true);
                                if (loc.equals("Trendyol")){
                                    table_products.listingProductsTrendyol(
                                            products_page_active_status.getValue(),
                                            products_page_barcode_input.getText(),
                                            products_page_sku_input.getText(),
                                            products_page_product_name_input.getText()
                                    );
                                }
                                if (loc.equals("Akınsoft")){
                                    table_products.listingProductsAkinsoft(
                                            products_page_active_status.getValue(),
                                            products_page_barcode_input.getText(),
                                            products_page_sku_input.getText(),
                                            products_page_product_name_input.getText()
                                    );
                                }
                                this.stop();
                            } catch (JSONException | IOException | SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    if (table_products.getStatus()){
                        System.out.println(logger.COLOR_BLUE+"Threadler Durduruldu"+logger.COLOR_RESET);
                        table_products.setStatus(false);
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                     products_table.refresh();
                      try {
                          Thread.sleep(200);
                      } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     t1.start();
                     try {
                         Thread.sleep(100);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
            if (loc.equals("Trendyol")){
                products_table.setItems(table_products.getProductsListTrendyol());
            }
            if (loc.equals("Akınsoft")){
                products_table.setItems(table_products.getProductsListAkinsoft());
            }
        });//END FILTER
        //END PRODUCTS PAGE
        products_page_right_pane_image.setOnMouseClicked((event) -> {
            String url = products_page_right_pane_image.getImage().impl_getUrl();
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) && url.startsWith("http")) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        button_save_last_enteg_time.setOnMouseClicked((event) -> {
            DateOperations.SaveLastEntegDate(getLast_enteg_hour().getText(),getLast_enteg_date());
        });
        settings_wolvox_logo.setOnMouseClicked((event) -> {
            ListAkinsoftProperties();
            anchor_main_page.setVisible(false);
            anchor_page_akinsoft_properties.setVisible(true);
        });
        akinsoft_settings_back_icon.setOnMouseClicked((event) -> {
            propertiesOperations.SaveAkinsoftProp(
                    akinsoft_settings_ip_adress.getText(),
                    akinsoft_settings_file_path.getText(),
                    akinsoft_settings_password.getText(),
                    akinsoft_settings_username.getText()
            );
            anchor_main_page.setVisible(true);
            anchor_page_akinsoft_properties.setVisible(false);
        });
        settings_trendyol_logo.setOnMouseClicked((event) -> {
            ListTrendyolProperties();
            anchor_main_page.setVisible(false);
            anchor_page_trendyol_settings.setVisible(true);
        });
        trendyol_settings_back_icon.setOnMouseClicked((event) -> {
            propertiesOperations.SaveTrendyolProperties(trendyol_settings_supplier_id.getText(),trendyol_settings_api_key.getText(),trendyol_settings_api_secret.getText());
            anchor_main_page.setVisible(true);
            anchor_page_trendyol_settings.setVisible(false);
        });
        settings_hepsiburada_logo.setOnMouseClicked((event) -> {
            ListHepsiburadaProperties();
            anchor_main_page.setVisible(false);
            anchor_page_hepsiburada_settings.setVisible(true);
        });
        hepsiburada_settings_back_icon.setOnMouseClicked((event) -> {
            propertiesOperations.SaveHepsiburadaProperties(hepsiburada_settings_merchant_id.getText(),hepsiburada_settings_username.getText(),hepsiburada_settings_password.getText());
            anchor_main_page.setVisible(true);
            anchor_page_hepsiburada_settings.setVisible(false);
        });
        //FORCE NUMERIC
        products_page_right_pane_1x1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    products_page_right_pane_1x1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        products_page_right_pane_1x2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    products_page_right_pane_1x1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }//END INITIALIZE

    public void StartingUtils(){
        
        products_page_right_pane_product_infos.setText(logger.COLOR_BLUE+"deneme");
        currenttimeLONG = System.currentTimeMillis();
        products_table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//çoklu satır seçme(Shift or CTRL)
        ObservableList<String> _activeStatStr = FXCollections.observableArrayList("Hepsi","Aktif","Pasif");
        products_page_active_status.setItems(_activeStatStr);
        ObservableList<String> _locationStatStr = FXCollections.observableArrayList("Akınsoft","Trendyol","Hepsiburada","N11","Gittigidiyor","Woocommerce");
        products_page_location_status.setItems(_locationStatStr);

        products_page_active_status.setValue("Hepsi");//default

        products_table_column_barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        products_table_column_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
        products_table_column_stock_name.setCellValueFactory(new PropertyValueFactory<>("stock_name"));
        products_table_product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        try {
            dateconvert();
            checkOrders();
        } catch (IOException | JSONException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void checkOrders() throws IOException, JSONException {
        API.API_trendyol.GETORDERcreated();
        if (order_trendyol>0){
            notification_cart_trendyol_icon.setVisible(true);
            notification_carttext_trendyol_icon.setVisible(true);
            notification_carttext_trendyol_icon.setText(String.valueOf(order_trendyol));
        }
        else{
            notification_cart_trendyol_icon.setVisible(false);
            notification_carttext_trendyol_icon.setVisible(false);
        }
    }
    public void ListTrendyolProperties(){
        JSONObject trendyol_prop = new JSONObject();
        try {
            trendyol_prop = (JSONObject)propertiesOperations.getPropOBJ().get("trendyol_api_properties");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        String s_supid = "";
        String s_key = "";
        String s_secret ="";
        try {
            s_key = trendyol_prop.getString("api_key");
            s_secret = trendyol_prop.getString("api_secret");
            s_supid = trendyol_prop.getString("supplier_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trendyol_settings_api_key.setText(s_key);
        trendyol_settings_api_secret.setText(s_secret);
        trendyol_settings_supplier_id.setText(s_supid);
    }
    public void ListHepsiburadaProperties(){
        JSONObject trendyol_prop = new JSONObject();
        try {
            trendyol_prop = (JSONObject)propertiesOperations.getPropOBJ().get("hepsiburada_api_properties");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        String s_supid = "";
        String s_key = "";
        String s_secret ="";
        try {
            s_key = trendyol_prop.getString("api_key");
            s_secret = trendyol_prop.getString("api_secret");
            s_supid = trendyol_prop.getString("supplier_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trendyol_settings_api_key.setText(s_key);
        trendyol_settings_api_secret.setText(s_secret);
        trendyol_settings_supplier_id.setText(s_supid);
    }
    public void ListAkinsoftProperties(){
        try {
            akinsoft_settings_ip_adress.setText(propertiesOperations.AkinsoftPropConverter().getString("s_ip"));
            akinsoft_settings_file_path.setText(propertiesOperations.AkinsoftPropConverter().getString("s_path"));
            akinsoft_settings_username.setText(propertiesOperations.AkinsoftPropConverter().getString("s_user"));
            akinsoft_settings_password.setText(propertiesOperations.AkinsoftPropConverter().getString("s_password"));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    public void dateconvert() throws IOException, JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String str_1 = DateOperations.getLastEntegDate("yyyy-MM-dd");
        String str_2 = DateOperations.getLastEntegDate("hh.mm.ss");
        last_enteg_hour.setText(str_2);
        LocalDate localDate = LocalDate.parse(str_1, formatter);
        last_enteg_date.setValue(localDate);
        String pattern = "yyyy-MM-dd";
        last_enteg_date.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
    public void resetFieldsProductsInfo(){
        products_page_right_pane_check_akinsoft.setSelected(false);
        products_page_right_pane_check_trendyol.setSelected(false);
        products_page_right_pane_check_hepsiburada.setSelected(false);
        products_page_right_pane_check_n11.setSelected(false);
        products_page_right_pane_check_gittigidiyor.setSelected(false);
        products_page_right_pane_check_woocommerce.setSelected(false);
        products_page_right_pane_1x1.clear();
        products_page_right_pane_1x2.clear();
        products_page_right_pane_1x3.clear();
        products_page_right_pane_2x1.clear();
        products_page_right_pane_2x2.clear();
        products_page_right_pane_2x3.clear();
        products_page_right_pane_3x1.clear();
        products_page_right_pane_3x2.clear();
        products_page_right_pane_3x3.clear();
        products_page_right_pane_4x1.clear();
        products_page_right_pane_4x2.clear();
        products_page_right_pane_4x3.clear();
        products_page_right_pane_5x1.clear();
        products_page_right_pane_5x2.clear();
        products_page_right_pane_5x3.clear();
        products_page_right_pane_6x1.clear();
        products_page_right_pane_6x2.clear();
        products_page_right_pane_6x3.clear();
    }
}
