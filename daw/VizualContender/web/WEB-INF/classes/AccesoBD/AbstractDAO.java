package AccesoBD;

import java.sql.*;

public abstract class AbstractDAO {
    private Connection conexion;
    private FachadaBD fachadaBD;

    public Connection getConexion(){
        try {
            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public FachadaBD getFachadaBD() {
        return fachadaBD;
    }

    public void setFachadaBD(FachadaBD fachadaBD) {
        this.fachadaBD = fachadaBD;
    }
}
