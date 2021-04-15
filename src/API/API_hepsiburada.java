package API;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.xml.sax.SAXException;
import utils.operations.propertiesOperations;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

public class API_hepsiburada {
    public static methods API = new methods();
    public static void GETallProducts() throws IOException, JSONException, ParserConfigurationException, SAXException {
        JSONObject hepsiburada_api_prop = (JSONObject) propertiesOperations.getPropOBJ().get("hepsiburada_api_properties");
        String merchant_id = hepsiburada_api_prop.getString("merchant_id");
        String username = hepsiburada_api_prop.getString("username");
        String password = hepsiburada_api_prop.getString("password");
        URL url = new URL("https://listing-external-sit.hepsiburada.com/listings/merchantid/"+merchant_id);
        String result = API.GET(url,username,password);
        org.jsoup.nodes.Document doc = Jsoup.parse(result, "", Parser.xmlParser());
        for (Element e : doc.select("listing")) {
            System.out.println(e);
            Element sku = e.select("MerchantSku").first();
            Element barcode = e.select("").first();
        }
    }
}
