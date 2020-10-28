package dao.impl;

import dao.InitializationDao;
import exceptions.DataProcessingException;
import util.ConnectionUtil;
import util.PropertiesUtil;

import java.io.*;
import java.sql.*;

public class InitializationDaoImpl implements InitializationDao {
    private Connection connection = ConnectionUtil.getConnection();
    private static final String PATH_TO_SQL_SCRIPT = PropertiesUtil.getPropertiesByKey("pathSqlScript");
    private static final String[] QUERIES = {
            "DROP TABLE IF EXISTS water_system CASCADE;",
            "DROP TABLE IF EXISTS water_node;",
            "CREATE TABLE water_node " +
                    "(id INT NOT NULL AUTO_INCREMENT, node VARCHAR(45) NULL, PRIMARY KEY (id));",
            "CREATE TABLE  water_system  ( " +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "start int NULL," +
                    "foreign key (start) references water_node (id)," +
                    "end int NULL," +
                    " foreign key(start) references water_node (id)," +
                    "len int NULL, PRIMARY KEY(id))"
    };


    @Override
    public void init() throws DataProcessingException, IOException, SQLException {
        try (Statement statement = connection.createStatement()) {
            for (String query : QUERIES) {
                statement.execute(query);
            }
        } catch (Exception e) {
            throw new DataProcessingException("Can't init");
        }
    }
}

