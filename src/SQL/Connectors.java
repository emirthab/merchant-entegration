package SQL;

import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONObject;
import utils.operations.propertiesOperations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectors {
    public Connection Connector_Akinsoft() throws SQLException, JSONException, IOException, org.json.JSONException {
        JSONObject akinsoft_prop = (JSONObject)propertiesOperations.getPropOBJ().get("akinsoft_properties");
        String host = akinsoft_prop.getString("host");
        String user = akinsoft_prop.getString("user");
        String pass = akinsoft_prop.getString("pass");
        Connection con = DriverManager.getConnection(host,user,pass);
        return con;
    }
}
