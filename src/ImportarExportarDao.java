
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
 * Todos direitos reservados a Tiago Dias de Souza
 * www.github.com/tiagods
 */

/**
 *
 * @author Tiago
 */
public class ImportarExportarDao {
    Connection con;
    void inserirBanco(List<MunicipioBean> lista){
        try{        
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost/municipios", "postgres", "root");
        System.out.println("Conectou");

        String importar= "insert into cad_municipio values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(importar);
        for(int i=0; i<lista.size();i++){
            ps.setInt(1,lista.get(i).getCodigo());
            ps.setString(2,lista.get(i).getNome());
            ps.setString(3,lista.get(i).getEstado());
            ps.executeUpdate();
            System.out.println("Registro "+lista.get(i).getCodigo()+" adicionado com sucesso!");
        }
        System.out.println("Todos os "+lista.size()+" incluidos com sucesso!");
        }catch(ClassNotFoundException | SQLException ex) {
        }finally{try{con.close();}catch(Exception e){}
        }
    }
    List<MunicipioBean> exportarBanco(){
            List<MunicipioBean> lista = new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost/municipios", "postgres", "root");
            System.out.println("Conectou");

            String exportar= "select * from cad_municipio";
            PreparedStatement ps = con.prepareStatement(exportar);
            ResultSet rs = ps.executeQuery();
            if(rs!=null){
                do{
                    MunicipioBean mb = new MunicipioBean();
                    mb.setCodigo(rs.getInt(1));
                    mb.setNome(rs.getString(2));
                    mb.setEstado(rs.getString(3));
                    lista.add(mb);
                }while(rs.next());
            }
            con.close();
            return lista;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Sem driver");
            return null;
        }
    }
}
