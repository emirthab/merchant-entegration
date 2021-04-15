package API;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class API_woocommerce {
    public static methods API = new methods();
    public static void deneme2() throws IOException, JSONException {
        String api_secret = "cs_037a88fead1c100b991a9d33d634ed4d11f12b9b";
        String api_key = "ck_14a1a107dcd1b37fb7780d1d4f782ecb7715824a";
        String host = "https://yagmurkultur.com";
        int page = 1;
        int lenght = 0;
        URL url = new URL(host + "/wp-json/wc/v3/products?per_page=100&page=" + page + "&consumer_key=" + api_key + "&consumer_secret=" + api_secret);
        String json = API.GET(url, api_key, api_secret);
        String _json2 = "";
        while(_json2 != null && !_json2.equals("[]")){
            page++;
            URL _url = new URL(host + "/wp-json/wc/v3/products?per_page=100&page=" + page + "&consumer_key=" + api_key + "&consumer_secret=" + api_secret);
            _json2 = API.GET(_url, api_key, api_secret);
            JSONArray obj_ = new JSONArray(_json2);
            System.out.println(obj_.length());
            lenght += obj_.length();
        }
        JSONArray _ar = new JSONArray(json);
        System.out.println(_ar.length());
    }
}
