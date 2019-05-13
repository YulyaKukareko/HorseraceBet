package by.epam.javawebtraining.kukareko.horseracebet.dao;

import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.DatabaseConnectionException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.TransactionNotCompleteException;
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

    protected static ConfigurationManager configurationManager;

    private PoolConnection pool;

    static {
        LOGGER = Logger.getLogger("DAOLayerLog");
        configurationManager = ConfigurationManager.getInstance();
    }

    public AbstractDAO() {
        pool = PoolConnection.getInstance();
    }

    protected final ResultSet executeProcedure(String procedureName, Map<Integer, Object> params, boolean isExecuteQuery) throws TransactionNotCompleteException {
        ResultSet result;
        Connection connection = pool.getConnection();

        try {
            CallableStatement statement = connection.prepareCall(procedureName);
            result = executeQuery(statement, params, isExecuteQuery);

        } catch (SQLException e) {
            throw new TransactionNotCompleteException("Transaction not completed. Try again");
        }
        pool.releaseConnection(connection);

        return result;
    }

    protected final ResultSet executeQuery(String query, Map<Integer, Object> params, boolean isExecuteQuery) throws IncorrectInputParamException, DatabaseConnectionException {
        ResultSet result;
        Connection connection = pool.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            result = executeQuery(statement, params, isExecuteQuery);

        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new IncorrectInputParamException(configurationManager.getProperty("inputParametersIncorrectMessage"));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DatabaseConnectionException(configurationManager.getProperty("databaseNotRespondingMessage"));
        }
        pool.releaseConnection(connection);

        return result;
    }

    private ResultSet executeQuery(PreparedStatement statement, Map<Integer, Object> params, boolean isExecuteQuery) throws SQLException {
        ResultSet result = null;

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
            case "Role":
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
