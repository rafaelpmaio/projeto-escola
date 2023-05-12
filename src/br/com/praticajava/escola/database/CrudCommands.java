package br.com.praticajava.escola.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CrudCommands {
	
	//Insere um novo aluna na base de dados, apenas com o nome do aluno
	public static void inserirAluno(String nome) {

		try {
			Connection connection = ConnectionMySql.connect();
			String sqlCommand = "INSERT INTO turma01 (name, primeira_nota, segunda_nota, terceira_nota, media_final, status) "
					+ "VALUES (?, null, null, null, null, null)";
			PreparedStatement statement = connection.prepareStatement(sqlCommand);
			statement.setString(1, nome);

			int executeUpdate = statement.executeUpdate();
			if (executeUpdate > 0) {
				System.out.println("Aluno inserido com sucesso.");
			} else {
				System.out.println("\nFalha ao inserir aluno.");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//solicita confirmação retornando o nome do aluno selecionado, após confirmação apaga linha do aluno
	public static void deletarAluno(String turma, int userId) {

		try {
			Connection connection = ConnectionMySql.connect();

			String sqlGetName = "SELECT name FROM "+ turma +" WHERE user_id = ?";
			PreparedStatement statementGetName = connection.prepareStatement(sqlGetName);
			statementGetName.setInt(1, userId);
			ResultSet queryGetName = statementGetName.executeQuery();
			queryGetName.next();
			String name = queryGetName.getString(1);

			System.out.printf(
					"Tem certeza que deseja apagar o aluno %s? A ação não poderá ser desfeita. \n Digite 1 para continuar\n",
					name);
			Scanner input = new Scanner(System.in);
			int continuar = input.nextInt();

			if (continuar == 1) {
				String sqlCommand = "DELETE FROM " + turma + " WHERE user_id = ?";
				PreparedStatement statement = connection.prepareStatement(sqlCommand);
				statement.setInt(1, userId);

				int executeUpdate = statement.executeUpdate();

				if (executeUpdate > 0) {
					System.out.println("Aluno deletado com sucesso");
				} else {
					System.out.println("Falha ao deletar aluno");
				}
			}
			input.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println("Encerrado");

	}

}
