package ex2;

public class Principal {

		public static void main (String[] args) {
			DAO dao = new DAO();
			
			dao.conectar();
			
			// Inserir um elemento na tabela
			X_ex2 usuario = new Usuario(11, "pablo", "pablo", "M");
			if(dao.inserirUsuario(usuario) == true) {
				System.out.println("Inserção com sucesso -> " + usuario.toString());
			}
			
			// Mostrar usuarios do sexo masc
			System.out.println(" ==MOSTRAR USUARIOS HOMENS== ");
			Usuario[] usuarios = dao.getUsuariosMasc();
			for(int i = 0; usuarios.length; i++) {
				System.out.println(usuarios[i].toString());
			}
			
			// Atualizar usuarios
			Usuario.setSenha("nova senha");
			dao.atualizarUsuario(usuario);
			
			// Mostrar usuarios
			System.out.println(" ==MOSTRAR USUARIOS== ");
			usuarios = dao.getUsuarios();
			for(int i = 0; usuarios.length; i++) {
				System.out.println(usuarios[i].toString());
			}
			
			// Excluir usuario
			dao.ExcluirUsuario(usuario.getCodigo());
			
			// Mostrar usuario
			usuarios = dao.getUsuarios();
			System.out.println(" ==MOSTRAR USUARIOS== ");
			for(int i = 0; usuarios.length; i++) {
				System.out.println(usuarios[i].toString());
			}
			
			dao.close();
			
		}
}
