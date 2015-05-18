package br.com.amigas.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.amigas.connectionFactory.ConnectionFactory;

public class ModeloDao { 
	private Connection connection;
	private static ModeloDao dao = new ModeloDao(); // objeto responsável por acessar os dados. Date access object

	//construtor responsavel para criar uma nova conexao
	public ModeloDao() { 
		this.connection = new ConnectionFactory().getConnection();
	}
	
	//Vai inserir informações na lista por meio da conexão com o MySQL
	private void inserir(Modelo modelo) { 
	     String sql = "insert into livros " +
	             "(nome,escritor,sinopse) values (?,?,?)";
	 
	     try {
	         // prepared statement para inserção
	         PreparedStatement stmt = connection.prepareStatement(sql);
	 
	         // seta os valores
	         stmt.setString(1,modelo.getNome());
	         stmt.setString(2,modelo.getEscritor());
	         stmt.setString(3, modelo.getSinopse());
	         
	         // executa
	         stmt.execute();
	         stmt.close();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }
	// Criar uma lista que vai selecionar todos os livros da tabela livros
	private List<Modelo> getLista() {
		try {
			List<Modelo> livros = new ArrayList<Modelo>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from livros.livros");
			ResultSet rs = stmt.executeQuery();
			//Vai criar um novo modelo  que vai especificar e guardar os dados
			while (rs.next()) {
				Modelo modelo = new Modelo();
				modelo.setId(rs.getInt("id"));
				modelo.setNome(rs.getString("nome"));
				modelo.setEscritor(rs.getString("escritor"));
				modelo.setSinopse(rs.getString("sinopse"));

				// adicionando o objeto à lista
				livros.add(modelo);
			}
			rs.close();
			stmt.close();
			return livros;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void delete(Modelo modelo) {
	try{
		// criando o statement e a query 
		PreparedStatement stmt = this.connection
				.prepareStatement("Delete from Livros where id = ?");
	
		// definindo pro statement procurar pelo ID que é um inteiro
		stmt.setLong(1, modelo.getId());
		// executando
		stmt.execute();
		// fechando
		stmt.close();
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}

	}
	// Vai atualizar os dados do mySQL
	private void update(Modelo modelo) { 
		String sql = "update livros set nome=?, escritor=?, sinopse=?  where id=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, modelo.getNome());
			stmt.setString(2, modelo.getEscritor());
			stmt.setString(3, modelo.getSinopse());
			stmt.setInt(4, modelo.getId());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	// Imprimir na tela as opções
	public static void menu(){
		System.out.println("**********************");
        System.out.println("         Menu");
        System.out.println("**********************");
        System.out.println("0. Fim");
        System.out.println("1. Incluir livro");
        System.out.println("2. Alterar livro");
        System.out.println("3. Excluir livro");
        System.out.println("4. Consultar livros");
        System.out.println("**********************");
        System.out.println("Opção:");
    }

	// pegar o numero inserido pelo usuario e vai direciona-lo a opção desejada
    public void opcoes() throws SQLException {
        int opcao;
        Scanner entrada = new Scanner(System.in);
        
        do{
        	menu();
            opcao = entrada.nextInt();
            
            switch(opcao){
            case 0:
            	System.out.println("Você saiu.");
            	break;
            case 1:
                inserir();
                break;
                
            case 2:
                atualizaLivro();
                break;
                
            case 3:
               deleta();
                break;
                
            case 4:
            	imprimirLivrosNaTela();
                break;
            
            default:
                System.out.println("Opção inválida.");
            }
        } while(opcao != 0);
    }

 
	

	private void deleta() throws SQLException {
		imprimirLivrosNaTela();
		System.out.println("Insira o numero do livro que você gostaria de deletar:  ");
		
		Scanner entrada = new Scanner(System.in);
		int numeroDigitado = entrada.nextInt();
		
		
		Modelo modelo = getLista().get(numeroDigitado);
		dao.delete(modelo);
		
		System.out.println("Livro deletado com sucesso!");
		
		
		
	}

	private void atualizaLivro() {
		imprimirLivrosNaTela();
		
		System.out.println("Insira o indice do livro que você gostaria de atualizar: ");
		
		Scanner entrada = new Scanner(System.in);
		int numeroDigitado = entrada.nextInt();	
		
		Modelo m = getLista().get(numeroDigitado);
		
		entrada = new Scanner(System.in);
		System.out.println("Insira o novo nome: ");
		m.setNome(entrada.nextLine());
		System.out.println("Insira o novo escritor: ");
		m.setEscritor(entrada.nextLine());
		System.out.println("Insira a nova sinopse: ");
		m.setSinopse(entrada.nextLine());
		
		dao.update(m);
		
		System.out.println("Livro atualizado com sucesso!");
	

	}

	private void imprimirLivrosNaTela() {
		List<Modelo> lista = getLista();
		
		int contador = 0;
		
		for (Modelo modelo : lista) {
			System.out.println("Indice: " + contador);
			System.out.println("Nome do livro: " + modelo.getNome());
			System.out.println("Escritor: " + modelo.getEscritor());
			System.out.println("Sinopse: " + modelo.getSinopse());
			System.out.println("--------------------------------");
			
			contador ++;
		}
		
		
	}

	private  void inserir() {
		
		Scanner entrada = new Scanner(System.in);
		Modelo m = new  Modelo();
		System.out.println("Nome: ");
		m.setNome(entrada.nextLine());
		System.out.println("Escritor: ");
		m.setEscritor(entrada.nextLine());
		System.out.println("Sinopse: ");
		m.setSinopse(entrada.nextLine());
	
		dao.inserir(m);
		
		System.out.println("Livro cadastrado com sucesso! ");
		
	}


}
