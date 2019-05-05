package by.epam.javawebtraining.kukareko.horseracebet.dao;

import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class AbstractDAO {

    protected static final Logger LOGGER;

    private PoolConnection pool;
    protected static ConfigurationManager configurationManager;


    static {
        LOGGER = Logger.getLogger("DAOLayerLog");
        configurationManager = ConfigurationManager.getInstance();
    }

    public AbstractDAO() {
        pool = PoolConnection.getInstance();
    }

    protected final ResultSet executeQuery(String query, Map<Integer, Object> params, boolean isExecuteQuery) throws SQLException {
        ResultSet result = null;
        Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        if (params != null) {
            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                defineFieldStatement(entry.getKey(), entry.getValue(), statement);
            }
        }

        if (isExecuteQuery) {
            result = statement.executeQuery();
        } else {
            statement.execute();
        }

        pool.releaseConnection(connection);
        return result;
    }

    private void defineFieldStatement(Integer index, Object param, PreparedStatement statement) throws SQLException {
        switch (param.getClass().getSimpleName()) {
            case "Integer":
                statement.setInt(index, (Integer) param);
                break;
            case "Float":
                statement.setFloat(index, (Float) param);
                break;
            case "Double":
                statement.setDouble(index, (Double) param);
                break;
            case "Long":
                statement.setDouble(index, (Long) param);
                break;
            case "BigDecimal":
                statement.setBigDecimal(index, (BigDecimal) param);
                break;
            case "Timestamp":
                statement.setTimestamp(index, (Timestamp) param);
                break;
            case "RaceType":
                statement.setString(index, param.toString());
                break;
            case "BetType":
                statement.setString(index, param.toString());
                break;
            case "Boolean":
                statement.setBoolean(index, (Boolean) param);
                break;
            default:
                if (param.equals("NULL")) {
                    statement.setNull(index, Types.INTEGER);
                } else {
                    statement.setString(index, (String) param);
                }
                break;
        }
    }
}
