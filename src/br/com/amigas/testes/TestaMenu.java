package br.com.amigas.testes;
import java.sql.SQLException;

import br.com.amigas.modelo.ModeloDao;

public class TestaMenu {
	public static void main(String[] args) throws SQLException{
		ModeloDao dao = new ModeloDao();
		dao.opcoes();
		/*try {
			dao.Menu();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	
	
	}


}
