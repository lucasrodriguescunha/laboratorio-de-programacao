package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Único ponto do sistema que sabe como abrir uma conexão com o MySQL.
 * Os DAOs pedem uma conexão nova a cada operação e a fecham com
 * try-with-resources.
 */
public final class ConnectionFactory {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CONFIG_FILE = "/database.properties";

    private static Properties config;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws DatabaseException {
        Properties settings = loadConfig();
        try {
            // Desde o JDBC 4 o DriverManager encontra o driver sozinho; carregá-lo
            // explicitamente deixa claro o que falta quando o JAR não está no projeto.
            Class.forName(DRIVER);
            return DriverManager.getConnection(
                    settings.getProperty("db.url"),
                    settings.getProperty("db.user"),
                    settings.getProperty("db.password", ""));
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Driver JDBC do MySQL não encontrado. "
                    + "Adicione o mysql-connector-j às bibliotecas do projeto.", e);
        } catch (SQLException e) {
            throw new DatabaseException(describe(e), e);
        }
    }

    public static String getUrl() throws DatabaseException {
        return loadConfig().getProperty("db.url");
    }

    private static synchronized Properties loadConfig() throws DatabaseException {
        if (config != null) {
            return config;
        }
        try (InputStream in = ConnectionFactory.class.getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new DatabaseException("Arquivo de configuração " + CONFIG_FILE + " não encontrado.");
            }
            Properties loaded = new Properties();
            loaded.load(in);
            if (loaded.getProperty("db.url") == null || loaded.getProperty("db.user") == null) {
                throw new DatabaseException("O arquivo " + CONFIG_FILE + " precisa definir db.url e db.user.");
            }
            config = loaded;
            return config;
        } catch (IOException e) {
            throw new DatabaseException("Não foi possível ler o arquivo " + CONFIG_FILE + ".", e);
        }
    }

    private static String describe(SQLException e) {
        if (e.getErrorCode() == 1045) {
            return "Usuário ou senha do MySQL incorretos. Confira o arquivo database.properties.";
        }
        if (e.getErrorCode() == 1049) {
            return "O banco de dados não existe. Execute o script sql/create-database.sql no MySQL.";
        }
        if (e.getSQLState() != null && e.getSQLState().startsWith("08")) {
            return "Não foi possível conectar ao MySQL. Verifique se o servidor está em execução "
                    + "e se o endereço em database.properties está correto.";
        }
        return "Erro ao conectar ao banco de dados: " + e.getMessage();
    }
}
