package fr.balen.paul.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseHelper {

    private static final String DEFAULT_DB_URL = "jdbc:sqlite:stockmania.db";
    private static String dbUrl = DEFAULT_DB_URL;

    public static void setDbUrl(String newUrl) {
        dbUrl = newUrl;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    public static <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T item = mapper.mapRow(rs);
                    if (item != null) {
                        results.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseHelper] Erreur executeQuery : " + e.getMessage() + " | SQL: " + sql);
        }
        return results;
    }

    public static <T> Optional<T> executeQuerySingle(String sql, RowMapper<T> mapper, Object... params) {
        List<T> list = executeQuery(sql, mapper, params);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.getFirst());
    }

    public static int executeUpdate(String sql, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[DatabaseHelper] Erreur executeUpdate : " + e.getMessage() + " | SQL: " + sql);
            return -1;
        }
    }

    private static void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
        }
    }
}
