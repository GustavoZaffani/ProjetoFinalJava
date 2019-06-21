package br.edu.utfpr.pb.ProjetoFinal.db;

import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection dbConnection;
    private EntityManagerFactory emf;
    private Connection conn;

    private DatabaseConnection() {
        try {
            this.emf = Persistence
                    .createEntityManagerFactory("ProjetoFinalJava");

            Session session = getEntityManager().unwrap(Session.class);
            SessionFactoryImplementor sessionFactoryImplementation
                    = (SessionFactoryImplementor) session.getSessionFactory();

            Connection c = sessionFactoryImplementation.
                    getSessionFactoryOptions().getServiceRegistry().
                    getService(ConnectionProvider.class).getConnection();
            this.conn = c;
        } catch (SQLException ex) {
            System.out.println("Erro ao estabelecer a conexão com o banco: ");
            ex.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DatabaseConnection();
        }
        return dbConnection;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Connection getConnection() {
        return conn;
    }
}
