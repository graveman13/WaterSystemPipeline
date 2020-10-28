package dao;

import exceptions.DataProcessingException;

import java.io.IOException;
import java.sql.SQLException;

public interface InitializationDao {
    void init() throws DataProcessingException, IOException, SQLException;
}
