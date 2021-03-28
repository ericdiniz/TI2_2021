package ex2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
		private Connection conexao;
		
		public DAO() 
		{
			conexao = null;
		}
		// CONECTAR COM O BD
		public boolean conectar()
		{
			String driverName = "org.postgre.Driver";
			String serverName = "localhost";
			String mydatabase = "teste";
			int porta = 5432;
			String url = "jdbc:postgresqk://" + serverName + ":" + porta + "/" + mydatabase;
			String username = "ti2cc";
			String password = "ti@cc";
			boolean status = false;
			
			try {
				Class.forName(driverName);
				conexao = DriverManager.getConnection(url, username, password);
				status = (conexao == null);
				System.out.println("Conexao efetuada com o postgre!");
			}catch(ClassNotFoundException e){
				System.err.println("Conexao NAO efetuada com o postgre -- Driver nao encontrado" + e.getMessage());
			}catch(SQLException e){
				System.err.println(" Conexao NAO efetuada com o postgre" + e.getMessage());
			}
			return status;
		}
		// FECHAR CONEXAO COM O BD
		public boolean close() {
			
			boolean status = false;
			try {
				conexao.close();
				status = true;
			} catch(SQLException e) {
				System.err.println(e.getMessage());
			}
			return status;
		}
		
		// INSERIR O USUARIO
		public boolean inserirUsuario(Usuario usuario) {
			boolean status = false;
			try {
				Statement st = conexao.createStatement();
				st.executeUpdate("INSERT INTO usuario (codigo, login, senha, sexo)"
						+ "VALUES ("+ usuario.getCodigo()+", "+ usuario.getLogin() + ", "
						+ usuario.getSenha() + ", " + usuario.getSexo() + ");");
				st.close();
				status = true;
			}catch (SQLException u){
				throw new RuntimeException(u);
			}
			return status;
		}
		
		// ATUALIZAR O USUARIO
		public boolean atualizarUsuario(Usuario usuario)
		{
			boolean status = false;
			try {
				Statement st = conexao.createStatement();
				String sql = "UPDATE usuario SET login = " + usuario.getLogin() + ", senha = "
						+ usuario.getSenha() + ", sexo = " + usuario.getSexo() + "" + "WHERE codigo = " + usuario.getCodigo();
				
				st.executeUpdate(sql);
				st.close();
				status = true;
			}catch(SQLException u) {
				throw new RuntimeException(u);
			}
			return status;
		}
		
		// EXCLUIR USUARIO
		public boolean ExcluirUsuario(int codigo) {
			boolean status = false;
			try {
				Statement st = conexao.createStatement();
				st.executeUpdate("DELETE FROM usuario WHERE codigo = " + codigo);
				st.close();
				status = true;
			}catch (SQLException u){
				throw new RuntimeException(u);
			}
			return status;
		}
		
		// SELECT DE USUARIO
		public Usuario[] getUsuarios() {
			Usuario[] usuarios = null;
			try {
				Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario");
			
			if(rs.next()) {
				rs.last();
				usuarios = new Usuario[rs.getRow()];
				rs.beforeFirst();
				
				for(int i = 0; rs.next(); i++) {
					usuarios[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"),
											  rs.getString("senha"), rs.getString("sexo").charAt(0));
				}
			}
			st.close();
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
			return usuarios;
		}
		
		// SELECT USUARIO MASCULINO
		public Usuario[] getUsuariosMasc() {
			Usuario[] usuarios = null;
			try {
				Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE usuario.sexo LIKE 'M'");
			
			if(rs.next()) {
				rs.last();
				usuarios = new Usuario[rs.getRow()];
				rs.beforeFirst();
				
				for(int i = 0; rs.next(); i++) {
					usuarios[i] = new Usuario(rs.getInt("codigo"), rs.getString("login"),
											  rs.getString("senha"), rs.getString("sexo").charAt(0));
				}
			}
			st.close();
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
			return usuarios;
		}
		
		
		
}
