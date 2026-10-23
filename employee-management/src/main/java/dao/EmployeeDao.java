package dao;

import db.ConnectionFactory;
import db.DatabaseException;
import entities.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Operações de CRUD na tabela funcionarios. Todos os comandos usam
 * PreparedStatement, que envia os valores separados do SQL e evita SQL injection.
 */
public class EmployeeDao {

    private static final String COLUMNS = "id, nome, cargo, data_contratacao, salario";

    private static final String INSERT =
            "INSERT INTO funcionarios (nome, cargo, data_contratacao, salario) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_NAME =
            "SELECT " + COLUMNS + " FROM funcionarios WHERE nome LIKE ? ORDER BY nome";
    private static final String SELECT_BY_ID =
            "SELECT " + COLUMNS + " FROM funcionarios WHERE id = ?";
    private static final String UPDATE =
            "UPDATE funcionarios SET nome = ?, cargo = ?, data_contratacao = ?, salario = ? WHERE id = ?";
    private static final String DELETE =
            "DELETE FROM funcionarios WHERE id = ?";

    public void insert(Employee employee) throws DatabaseException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            fillStatement(stmt, employee);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    employee.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao cadastrar funcionário: " + e.getMessage(), e);
        }
    }

    public List<Employee> findAll() throws DatabaseException {
        return findByName("");
    }

    public List<Employee> findByName(String name) throws DatabaseException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NAME)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Employee> employees = new ArrayList<>();
                while (rs.next()) {
                    employees.add(toEmployee(rs));
                }
                return employees;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao listar funcionários: " + e.getMessage(), e);
        }
    }

    public Employee findById(int id) throws DatabaseException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? toEmployee(rs) : null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }
    }

    public boolean update(Employee employee) throws DatabaseException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            fillStatement(stmt, employee);
            stmt.setInt(5, employee.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    public boolean delete(int id) throws DatabaseException {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao excluir funcionário: " + e.getMessage(), e);
        }
    }

    private void fillStatement(PreparedStatement stmt, Employee employee) throws SQLException {
        stmt.setString(1, employee.getName());
        stmt.setString(2, employee.getRole());
        stmt.setObject(3, employee.getHireDate());
        stmt.setBigDecimal(4, employee.getSalary());
    }

    private Employee toEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cargo"),
                rs.getObject("data_contratacao", LocalDate.class),
                rs.getBigDecimal("salario"));
    }
}
