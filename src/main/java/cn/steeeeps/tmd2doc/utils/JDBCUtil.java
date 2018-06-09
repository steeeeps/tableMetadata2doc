package cn.steeeeps.tmd2doc.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import cn.steeeeps.tmd2doc.entity.ColumnMetadata;
import cn.steeeeps.tmd2doc.entity.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCUtil {

    private Logger logger = LoggerFactory.getLogger(JDBCUtil.class);

    private String driverClass;
    private String url;
    private String user;
    private String password;

    private Connection connection = null;
    private PreparedStatement statement = null;

    public JDBCUtil(String driverClass, String url, String user, String password) {

        this.driverClass = driverClass;
        this.url = url;
        this.user = user;
        this.password = password;


    }

    public Connection getConnection() {
        if (this.connection != null)
            return connection;

        try {
            Class.forName(this.driverClass);
        } catch (ClassNotFoundException ex) {
            logger.error("can't load database driver," + ex.getMessage());
        }
        try {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("connect to database failure," + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            logger.error("close database connection failure," + e1.getMessage());
        }

    }

    public void startTranction() {
        try {
            if (connection != null)
                connection.setAutoCommit(false);
            else {
                logger.error("databse connection is null");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
    }

    public void commit() {
        try {
            if (connection != null)
                connection.commit();
            else {
                logger.error("databse connection is null");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }

    }

    public void roolback() {
        try {
            if (connection != null)
                connection.rollback();
            else {
                logger.error("databse connection is null");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
    }

    public boolean executeSql(String sql) {

        PreparedStatement statement = null;
        try {
            getConnection();

            statement = connection.prepareStatement(sql);

            startTranction();
            statement.executeUpdate();
            commit();
            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            roolback();
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                logger.error(e1.getMessage());
            }
            closeConnection();
        }

    }

    public ResultSet query(String sql) {
        ResultSet result = null;

        try {
            getConnection();
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());

        }
        return result;
    }

    public List<TableMetadata> listAllTables() {

        DatabaseMetaData databaseMetaData = null;
        List<TableMetadata> metadataList = new ArrayList<TableMetadata>();
        try {
            databaseMetaData = this.connection.getMetaData();

            ResultSet resultSet = databaseMetaData.getTables(null, "public", null, new String[]{"TABLE"});
            while (resultSet.next()) {
                metadataList.add(new TableMetadata(resultSet.getString("TABLE_NAME"),
                        resultSet.getString("REMARKS")));
            }

        } catch (SQLException e) {
            logger.error("get databse table names failure" + e.getMessage());
        }
        return metadataList;

    }

    public List<ColumnMetadata> getTableMetaData(String tableName) {
        List<ColumnMetadata> columnMetadataList = new ArrayList<>();

        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null);

            while (columns.next()) {
                ColumnMetadata columnMetadata = new ColumnMetadata();
                columnMetadata.setColumnName(columns.getString("COLUMN_NAME"));
                columnMetadata.setDataType(columns.getString("TYPE_NAME"));
                columnMetadata.setColumnSize(columns.getInt("COLUMN_SIZE"));
                columnMetadata.setDecimaldigits(columns.getInt("DECIMAL_DIGITS"));
                columnMetadata.setNullable(columns.getString("IS_NULLABLE"));
                columnMetadata.setAutoIncrment(columns.getString("IS_AUTOINCREMENT"));
                columnMetadata.setRemark(columns.getString("REMARKS"));
                columnMetadataList.add(columnMetadata);
            }

        } catch (Exception ex) {
            logger.error("get column metadata failure" + ex.getMessage());
        }

        return columnMetadataList;
    }


}
