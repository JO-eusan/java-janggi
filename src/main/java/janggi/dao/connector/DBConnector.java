package janggi.dao.connector;

import java.sql.PreparedStatement;

public interface DBConnector {

    PreparedStatement handleQuery(String query);

    void close();
}
