package lt.mikasdu.ui.sqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    void saveToDatabase();

    void updateDatabase();

    void removeFromDatabase();

    void setParam(ResultSet resultSet) throws SQLException;

    String getObjectSqlStatement();
}