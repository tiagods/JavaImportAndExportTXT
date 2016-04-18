
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Todos direitos reservados a Tiago Dias de Souza
 * www.github.com/tiagods
 */

/**
 *
 * @author Tiaog
 */
public class ImportarExportar {
    public static void main(String[] args){
        new ImportarExportar();
    }
    
    public ImportarExportar(){
        //importar();
        //exportar();
    }
    public static void importar(){
        //criando lista para pegar valores
        List<MunicipioBean> lista = new ArrayList<>();
            
        try (
            BufferedReader bf = new BufferedReader(new FileReader("importar.txt"))) {
                String linha;
                String dados[];

                MunicipioBean mb;
                for(int i=0; ;i++){
                    linha = bf.readLine();
                    if(linha == null) break;
                    dados = linha.split(";");
                    mb = new MunicipioBean();
                    mb.setCodigo(Integer.parseInt(dados[0]));
                    mb.setNome(dados[1]);
                    mb.setEstado(dados[2]);
                    lista.add(mb);
                }
            for(MunicipioBean mun : lista) {
                System.out.println("Codigo: "+mun.getCodigo()
                        +"\t\tNome: "+mun.getNome()
                        +"\t\tEstado: "+mun.getEstado()+"\n");
            }
            //inserindo lista no banco
            ImportarExportarDao inserir = new ImportarExportarDao();
            inserir.inserirBanco(lista);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportarExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void exportar(){
        List<MunicipioBean> lista = new ArrayList<>();
        ImportarExportarDao listaDao = new ImportarExportarDao();
        //pegando dados do banco
        lista = listaDao.exportarBanco();
        if(lista!=null){
            try(
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File("exportar.txt")))) {
                    String linha;
                    for(MunicipioBean mb : lista){
                        linha = mb.getCodigo()+";";
                        linha+=mb.getNome()+";";
                        linha+=mb.getEstado();
                        bw.write(linha);
                        bw.newLine();
                    }
            } catch (IOException ex) {
            }
        }else
            System.out.println("Nenhum dado foi recuperado");
    }
}
