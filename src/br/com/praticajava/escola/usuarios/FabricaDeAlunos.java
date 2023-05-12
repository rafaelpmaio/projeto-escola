package br.com.praticajava.escola.usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.praticajava.escola.database.ConnectionMySql;

public class FabricaDeAlunos {
	
	List<Aluno> alunos;
	
	public static void gerarAlunos(String turma) {

		try {
			Connection connection = ConnectionMySql.connect();
			String sqlCommand = "SELECT * FROM ?";
			PreparedStatement statement = connection.prepareStatement(sqlCommand);
			statement.setString(1, sqlCommand);
			
			ResultSet query = statement.executeQuery();
			int totalAlunos = 0;

			while (query.next()) {
				int userId = query.getInt(1);
				String name = query.getString(2);
				double[] notas = new double[3];
				notas[0] = query.getDouble(3);
				notas[1] = query.getDouble(4);
				notas[2] = query.getDouble(5);
				double mediaFinal = query.getDouble(6);
				String status = query.getString(7);

				String output = "id#%d - Aluno %s - 1a:%.2f - 2a:%.2f - 3a:%.2f - mf:%.2f - %s";
				System.out.println(String.format(output, userId, name, notas[0], notas[1], notas[2],
						mediaFinal, status));
				
				Aluno aluno = new Aluno(userId, name, notas, mediaFinal);
				totalAlunos++;
			}
			System.out.println("Total de alunos na turma: " + totalAlunos);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	

}
}
