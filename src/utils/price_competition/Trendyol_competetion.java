package utils.price_competition;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Trendyol_competetion {
    public static void getStoreActiveProducts() throws IOException {
        String merchantLink = "https://www.trendyol.com/tum--urunler?satici=226811";
        int pi =1;
        String str = "";
        ArrayList<String> _ARhref = new ArrayList<>();
        while(str!=null){
            Document doc = Jsoup.connect(merchantLink+"&pi="+pi).get();
            Element body = doc.body();
            Elements divs = body.getElementsByClass("p-card-chldrn-cntnr");
            for (Element div : divs) {
                Element link = div.select("a[href]").first();
                str = link.attr("href");
                _ARhref.add(str);
                System.out.println(str);
            }
            if(divs.size()==0){
                str =null;
            }
            pi++;
        }
        for(int i=0;i< _ARhref.size();i++){
            String _str = _ARhref.get(i);
            System.out.println(i+" "+_str);
        }
    }
}
