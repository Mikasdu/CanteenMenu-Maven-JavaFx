package lt.mikasdu;

import lt.mikasdu.ui.sqlConnection.Database;
import lt.mikasdu.ui.sqlConnection.SqlConnection;
import lt.mikasdu.ui.sqlConnection.SqlStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeekMenu implements Database {

    private int id;
    private String name;
    private boolean status;

    public WeekMenu() {
        setId(0);
        setName("New");
        setStatus(false);
    }

    public WeekMenu(int id, String name, boolean status) {
        setId(id);
        setName(name);
        setStatus(status);
    }

    public String toString() {
        return getName();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void saveToDatabase() {
        SqlConnection.updateDatabase(SqlStatement.INSERT_WEEK_MENU, this.getName());
    }

    @Override
    public void updateDatabase() {
        SqlConnection.updateDatabase(SqlStatement.UPDATE_WEEK_MENU_ITEM,
                this.getName(),
                this.isStatus(),
                this.getId()
        );
    }

    @Override
    public void removeFromDatabase() {
        this.setStatus(false);
        this.updateDatabase();
    }

    @Override
    public void setParam(ResultSet resultSet) throws SQLException {

    }

    @Override
    public String getObjectSqlStatement() {
        return null;
    }


}
