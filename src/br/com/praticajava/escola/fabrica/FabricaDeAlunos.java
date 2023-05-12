package br.com.praticajava.escola.fabrica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.praticajava.escola.database.ConnectionMySql;
import br.com.praticajava.escola.usuarios.Aluno;

public class FabricaDeAlunos {

	//método gera os Alunos presentes na turma selecionada com as informações presentes na base de dados
	public static ArrayList<Aluno> gerarAlunosPorTurma(String turma) {

		try {
			Connection connection = ConnectionMySql.connect();
			String sqlCommand = "SELECT * FROM " + turma;
			Statement statement = connection.createStatement();

			ResultSet query = statement.executeQuery(sqlCommand);
			ArrayList<Aluno> alunos = new ArrayList<>();

			while (query.next()) {
				int userId = query.getInt(1);
				String name = query.getString(2);
				double[] notas = new double[3];
				notas[0] = query.getDouble(3);
				notas[1] = query.getDouble(4);
				notas[2] = query.getDouble(5);
				double mediaFinal = query.getDouble(6);
				String status = query.getString(7);

				int isAproved = 0;
				if (status != null) {
					if (status.equals(Aluno.Status.APROVADO.name())) {
						isAproved = 1;
					} else {
						isAproved = 0;
					}
				}

				Aluno aluno = new Aluno(userId, name, notas, mediaFinal, isAproved);
				alunos.add(aluno);
			}
			return alunos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
