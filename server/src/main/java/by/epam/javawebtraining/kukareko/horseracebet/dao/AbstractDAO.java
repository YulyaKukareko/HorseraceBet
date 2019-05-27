package by.epam.javawebtraining.kukareko.horseracebet.dao;

import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.DatabaseConnectionException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.IncorrectInputParamException;
import by.epam.javawebtraining.kukareko.horseracebet.model.exception.logical.TransactionNotCompleteException;
import by.epam.javawebtraining.kukareko.horseracebet.util.ConfigurationManager;
import by.epam.javawebtraining.kukareko.horseracebet.util.constant.*;
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

    private String inputParametersIncorrectMes;
    private String transactionNotCompletedMes;
    private String databaseNotRespondingMes;

    private PoolConnection pool;

    static {
        LOGGER = Logger.getLogger(LogConstant.DAO_LAYER_LOG);
        configurationManager = ConfigurationManager.getInstance();
    }

    public AbstractDAO() {
        this.pool = PoolConnection.getInstance();
        this.inputParametersIncorrectMes = configurationManager.getProperty(ExceptionMessageConstant.INPUT_PARAMETERS_INCORRECT_MESSAGE);
        this.transactionNotCompletedMes = configurationManager.getProperty(ExceptionMessageConstant.TRANSACTION_NOT_COMPLETED_MESSAGE);
        this.databaseNotRespondingMes = configurationManager.getProperty(ExceptionMessageConstant.DATABASE_NOT_RESPONDING_MESSAGE);
    }

    /**
     * @param procedureName
     * @param params
     * @param isExecuteQuery
     * @return
     * @throws TransactionNotCompleteException
     */
    protected final ResultSet executeProcedure(String procedureName, Map<Integer, Object> params, boolean isExecuteQuery)
            throws TransactionNotCompleteException {
        ResultSet result;
        Connection connection = pool.getConnection();

        try {
            CallableStatement statement = connection.prepareCall(procedureName);
            result = executeQuery(statement, params, isExecuteQuery);

        } catch (SQLException e) {
            throw new TransactionNotCompleteException(transactionNotCompletedMes);
        }
        pool.releaseConnection(connection);

        return result;
    }

    /**
     *
     * @param query
     * @param params
     * @param isExecuteQuery
     * @return execution result query
     * @throws IncorrectInputParamException
     * @throws DatabaseConnectionException
     */
    protected final ResultSet executeQuery(String query, Map<Integer, Object> params, boolean isExecuteQuery)
            throws IncorrectInputParamException, DatabaseConnectionException {
        ResultSet result;
        Connection connection = pool.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            result = executeQuery(statement, params, isExecuteQuery);

        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new IncorrectInputParamException(inputParametersIncorrectMes);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new DatabaseConnectionException(databaseNotRespondingMes);
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
            case ParamTypeConstant.INTEGER_VALUE:
                statement.setInt(index, (Integer) param);
                break;
            case ParamTypeConstant.FLOAT_VALUE:
                statement.setFloat(index, (Float) param);
                break;
            case ParamTypeConstant.DOUBLE_VALUE:
                statement.setDouble(index, (Double) param);
                break;
            case ParamTypeConstant.LONG_VALUE:
                statement.setDouble(index, (Long) param);
                break;
            case ParamTypeConstant.BIG_DECIMAL_VALUE:
                statement.setBigDecimal(index, (BigDecimal) param);
                break;
            case ParamTypeConstant.TIMESTAMP_VALUE:
                statement.setTimestamp(index, (Timestamp) param);
                break;
            case ParamTypeConstant.RACE_TYPE_VALUE:
                statement.setString(index, param.toString());
                break;
            case ParamTypeConstant.ROLE_VALUE:
                statement.setString(index, param.toString());
                break;
            case ParamTypeConstant.BET_TYPE_VALUE:
                statement.setString(index, param.toString());
                break;
            case ParamTypeConstant.BOOLEAN_VALUE:
                statement.setBoolean(index, (Boolean) param);
                break;
            default:
                if (param.equals(SQLConstant.NULL_VALUE)) {
                    statement.setNull(index, Types.INTEGER);
                } else {
                    statement.setString(index, (String) param);
                }
                break;
        }
    }
}
