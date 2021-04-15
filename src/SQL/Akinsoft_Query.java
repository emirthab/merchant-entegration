package SQL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Akinsoft_Query {
    public static Connectors con = new Connectors();
    public static ResultSet BarkodSorgu(String barcodes) throws IOException, SQLException, JSONException {
        String seperator="";
        if (!barcodes.contains("'")){
            seperator = "'";
        }
        String selected = "STOKHR.BLSTKODU,STOKHR.TUTAR_TURU,STOKHR.MIKTARI,STOK.BIRIMI,STOK.BARKODU,STOK.STOK_ADI,STOK.BLKODU,STOK.STOKKODU";
        String froms = "STOKHR,STOK";
        String wheres = "STOK.BLKODU = STOKHR.BLSTKODU AND STOK.BARKODU = "+seperator + barcodes + seperator;
        String Query = "SELECT " + selected + " FROM " + froms + " WHERE " + wheres;
        Statement stat = con.Connector_Akinsoft().createStatement();
        return stat.executeQuery(Query);
    }
    public static ResultSet DateQueryStokHR(String dateBEGIN,String dateEND, Integer tutarturu) throws IOException, SQLException, JSONException {
        String selected = "STOKHR.TARIHI,STOKHR.BLSTKODU,STOKHR.TUTAR_TURU,STOKHR.MIKTARI,STOK.BIRIMI,STOK.BARKODU,STOK.STOK_ADI,STOK.BLKODU,STOK.STOKKODU";
        String froms = "STOKHR,STOK";
        String wheres = "STOK.BLKODU = STOKHR.BLSTKODU AND STOKHR.TARIHI >= '"+dateBEGIN+"' AND STOKHR.TARIHI <= '"+dateEND+"'";
        if (tutarturu!=null){
            wheres += " AND TUTAR_TURU = "+tutarturu;
        }
        String Query = "SELECT " + selected + " FROM " + froms + " WHERE " + wheres;
        Statement stat = con.Connector_Akinsoft().createStatement();
        return stat.executeQuery(Query);
    }
    public void HRyazdir(int blkodu, int blstkodu, int tutarturu, String tarihi,String evrakno,String aciklama, int miktar,double birimfiyat,double toplamfiyat,String carikodu, String birim, Integer girenMik, Integer cikanMik,String kaydeden) throws IOException, SQLException, JSONException {
        String tablename ="STOKHR";
        String values =
                blkodu+","
                        +blstkodu+","
                        +tutarturu+",'"
                        +tarihi+"','"
                        +evrakno+"'," //evrak no
                        +aciklama+"," //aciklama
                        +miktar+","
                        +toplamfiyat+"," //kpb_tutarı
                        +birimfiyat+"," //kpb_fiyatı
                        +null+"," //doviz tutarı
                        +null+"," //doviz fiyati
                        +null+"," //ozel kodu
                        +carikodu+"," //carikodu
                        +0+"," //dovizkullan
                        +1+"," //kpbdvz
                        +null+"," //doviz_birimi
                        +0+"," //doviz_hes_isle
                        +null+"," //doviz_alis
                        +null+"," //doviz_satic
                        +null+"," //depo_adi
                        +miktar+"," //miktar2
                        +girenMik+"," //kpb_gmik
                        +cikanMik+"," //kpb_cmik
                        +null+"," //kpb_gtut
                        +null+"," //kpb_ctut
                        +null+"," //dvz_gmik
                        +null+"," //dvz_cmik
                        +null+"," //dvz_gtut
                        +null+"," //dvz_ctut
                        +null+"," //entegrasyon
                        +0+"," //devir
                        +0+"," //iade
                        +0+"," //silindi
                        +"'"+kaydeden+"'"+"," //kaydeden
                        +null+"," //degistiren
                        +null+"," //degistirilme_tarihi
                        +null+"," //bldepotrs_kodu
                        +null+"," //ekbilgi1
                        +null+"," //ekbilgi2
                        +null+"," //ekbilgi3
                        +null+"," //ekbilgi4
                        +1+",'" //maliyete_ekle
                        +birim+"'," //birim_2
                        +null+"," //ana_stokkkodu
                        +null+"," //sube_kodu
                        +null+"," //gm_entegrasyon
                        +null+"," //transfer_irsaliyesi
                        +null+"," //ek_maliyet_kbp
                        +null+"," //ek_maliyet_dvz
                        +null+"," //offline_durum
                        +null+"," //blfhkodu
                        +null+",'" //source_app
                        +tarihi+"'," //kayit tarihi
                        +null; //blsayimkodu
        String Query = "INSERT INTO " + tablename + " VALUES (" + values+")";
        Statement stat = con.Connector_Akinsoft().createStatement();
        stat.executeUpdate(Query);
        System.out.println(Query);
    }
    public void Update_lastBL(int blkodu) throws IOException, SQLException, JSONException {
        Statement stat = con.Connector_Akinsoft().createStatement();
        String Query = "UPDATE GEN_IDT SET GEN_VALUE = "+blkodu+" WHERE GEN_NAME = 'STOKHR_GEN'";
        stat.executeUpdate(Query);
    }
    public static ResultSet BLKODsorgu() throws IOException, SQLException, JSONException {
        String selected = "MAX (BLKODU)";
        String froms = "STOKHR";
        String Query = "SELECT " + selected + " FROM " + froms;
        Statement stat = con.Connector_Akinsoft().createStatement();
        ResultSet res = stat.executeQuery(Query);
        return res;
    }
    public static JSONObject FIYATsorguTekil(String barcode, String sku) throws JSONException, SQLException, IOException {
        JSONObject _obj = new JSONObject();
        String seperator="";
        String selected = "STOK_FIYAT.TANIMI,STOK_FIYAT.BLSTKODU,STOK_FIYAT.FIYATI,STOK.BARKODU,STOK.STOK_ADI,STOK.BLKODU,STOK.STOKKODU";
        String froms = "STOK_FIYAT,STOK";
        String wheres = "STOK.BLKODU = STOK_FIYAT.BLSTKODU";
        if(barcode!=null) {
            if (!barcode.contains("'")){
                seperator = "'";
            }
            wheres += " AND STOK.BARKODU = "+seperator + barcode + seperator;
        }if(sku!=null) {
            if (!sku.contains("'")){
                seperator = "'";
            }
            wheres += " AND STOK.STOKKODU = "+seperator + sku + seperator;
        }
        String Query = "SELECT " + selected + " FROM " + froms + " WHERE " + wheres;
        Statement stat = con.Connector_Akinsoft().createStatement();
        ResultSet res = stat.executeQuery(Query);
        while(res.next()){
            String tanim = res.getString("TANIMI");
            double price = res.getDouble("FIYATI");
            if (tanim.equals("ALIS FİYATI -1")||tanim.equals("ALIŞ FİYATI -1")){
                _obj.put("alis",price);
            }
            if (tanim.equals("SATIS FİYATI -1")||tanim.equals("SATIŞ FİYATI -1")){
                _obj.put("satis",price);
            }
        }
        return _obj;
    }
    public static ResultSet STOKsorguForProductsTable(String wheres) throws JSONException, SQLException, IOException {
        String Query = "SELECT BARKODU,STOK_ADI,STOKKODU FROM STOK "+wheres;
        Statement stat = con.Connector_Akinsoft().createStatement();
        return stat.executeQuery(Query);
    }
    public static void GetDateStokHR(String dateBegin,String dateEnd) throws JSONException, SQLException, IOException {
        ResultSet res = Akinsoft_Query.DateQueryStokHR(dateBegin,dateEnd,null);
        while(res.next()){
            System.out.println(res.getString("BLSTKODU"));
            System.out.println(res.getTimestamp("TARIHI"));
        }
    }
    public static JSONArray getHRbasic(ResultSet res) throws SQLException, JSONException {
        JSONArray _ar1 = new JSONArray();
        while(res.next()){
            JSONObject _obj = new JSONObject();
            _obj.put("sku",res.getString("STOKKODU"));
            _obj.put("barcode",res.getString("BARKODU"));
            _obj.put("quantity",res.getInt("MIKTARI"));
            _ar1.put(_obj);
        }
        return _ar1;
    }
    public static int stokmiktar(String barkod) throws JSONException, SQLException, IOException {
        ResultSet sorgu1 = BarkodSorgu(barkod);
        int ztotal = 0;
        while(sorgu1.next()){
            int tutarturu = sorgu1.getInt("TUTAR_TURU");
            int miktari = sorgu1.getInt("MIKTARI");
            if (tutarturu == 0) {
                ztotal -= miktari;
            }else{
                ztotal += miktari;
            }
        }
        return ztotal;
    }
    public static boolean isBarcodeAvailable(String barcode) throws IOException, JSONException {
        String seperator="";
        if (!barcode.contains("'")){
            seperator = "'";
        }
        String selected = "BARKODU";
        String froms = "STOK";
        String wheres = "BARKODU = "+seperator + barcode + seperator;
        String Query = "SELECT " + selected + " FROM " + froms + " WHERE " + wheres;
        Statement stat = null;
        try {
            stat = con.Connector_Akinsoft().createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        boolean bol = false;
        try {
            ResultSet res = stat.executeQuery(Query);
            while (res.next()){
                if (res.getString("BARKODU").equals(barcode)) {
                    bol = true;
                }
            }
        } catch (SQLException throwables) {
            bol = false;
        }
        return bol;
    }
}
