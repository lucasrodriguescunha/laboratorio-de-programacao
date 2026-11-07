// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.conexao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Fábrica de conexões usada por todas as telas.
 *
 * O código dos botões fornecido na atividade abre a conexão com
 * ConnectionFactory.getConnection(). Esta classe mantém esse nome e repassa
 * o pedido para a classe Conexao, que é quem guarda a URL, o usuário e a
 * senha e carrega o driver. Assim alunos, professores e cursos usam
 * exatamente a mesma classe de conexão.
 */
public class ConnectionFactory {

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        return Conexao.getConnection();
    }
}
