// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de conexão com o banco de dados MySQL "escola".
 *
 * Guarda a URL do banco, o usuário e a senha, carrega o driver JDBC
 * (MySQL Connector/J) e abre a conexão no método getConnection().
 * Se o MySQL do computador usar outro usuário ou outra senha, basta
 * alterar as constantes USUARIO e SENHA abaixo.
 */
public class Conexao {

    // Classe do driver que fica dentro do arquivo mysql-connector-j-9.1.0.jar.
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // jdbc:mysql:// é o protocolo, localhost:3306 é o endereço e a porta do
    // servidor e escola é o nome do banco. Os parâmetros depois do "?" permitem
    // conectar a um MySQL 8 instalado no próprio computador sem certificado SSL.
    private static final String URL =
            "jdbc:mysql://localhost:3306/escola?sslMode=DISABLED&allowPublicKeyRetrieval=true";

    private static final String USUARIO = "root";
    private static final String SENHA = "admin";

    private Conexao() {
    }

    public static Connection getConnection() throws SQLException {
        // Carregamento do driver JDBC. Desde o JDBC 4 o DriverManager encontra o
        // driver sozinho, mas carregá-lo aqui permite avisar com clareza quando o
        // .jar não foi adicionado às bibliotecas do projeto.
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado. Adicione o arquivo "
                    + "mysql-connector-j em Project Properties > Libraries > Add JAR/Folder.", e);
        }

        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            // Troca a mensagem técnica do MySQL por uma explicação em português,
            // mantendo o erro original como causa.
            throw new SQLException(explicar(e), e.getSQLState(), e.getErrorCode(), e);
        }
    }

    // Traduz os erros de conexão mais comuns em uma mensagem que diz o que fazer.
    private static String explicar(SQLException e) {
        if (e.getErrorCode() == 1045) {
            return "Usuário ou senha do MySQL incorretos. Ajuste USUARIO e SENHA na classe Conexao.";
        }
        if (e.getErrorCode() == 1049) {
            return "O banco de dados \"escola\" não existe. Execute o arquivo script-sql.txt no MySQL.";
        }
        if (e.getSQLState() != null && e.getSQLState().startsWith("08")) {
            return "Não foi possível conectar ao MySQL em localhost:3306. "
                    + "Verifique se o serviço do MySQL está em execução.";
        }
        return e.getMessage();
    }
}
