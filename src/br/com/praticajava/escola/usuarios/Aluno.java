package br.com.praticajava.escola.usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Aluno extends Usuario {

	private int id;
	private double[] notas = new double[3];
	private double mediaFinal;
	private int status;

	public enum Status {
		APROVADO, REPROVADO
	};

	public Aluno(int id, String nome, double[] notas, double mediaFinal, int status) {
		super();
		this.id = id;
		this.nome = nome;
		this.notas = notas;
		this.mediaFinal = mediaFinal;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public double[] getNotas() {
		return notas;
	}

	private double mediaNotas() {
		double totalNotas = 0;
		double mediaNotas;

		for (int i = 0; i < notas.length; i++) {
			totalNotas += this.notas[i];
		}

		mediaNotas = totalNotas / notas.length;

		return mediaNotas;
	}

	public void isAprovado(Connection connection, int trimestre, double nota) {
		try {
			String sqlCommand = "UPDATE turma01 SET media_final = ?, status = ? WHERE user_id = ?";
			if (trimestre == 1) {
				this.notas[0] = nota;
			}
			if (trimestre == 2) {
				this.notas[1] = nota;
			}
			if (trimestre == 3) {
				this.notas[2] = nota;
			}
			
			PreparedStatement statement = connection.prepareStatement(sqlCommand);
			statement.setDouble(1, this.mediaNotas());
			if (this.mediaNotas() < 7) {
				statement.setString(2, Status.REPROVADO.name());
			} else {
				statement.setString(2, Status.APROVADO.name());
			}
			statement.setInt(3, this.id);

			int executeUpdate = statement.executeUpdate();
			if (executeUpdate > 0) {
				System.out.println("média final atualizada no sistema");
			} else {
				System.out.println("falha ao atualizar média final");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//retorna impressão das notas, media final e status do aluno
	public void boletim() {

		System.out.println("Aluno: " + this.nome + " #" + this.id);
		for (int i = 0; i < notas.length; i++) {
			System.out.printf("%do Trimestre: %.2f \n", i + 1, notas[i]);
		}
		System.out.printf("Media final do aluno: %.2f\n", this.mediaNotas());
		if (this.status == 1) {
			System.out.println("Status: " + Status.APROVADO.name() + "\n");
		} else {
			System.out.println("Status: " + Status.REPROVADO.name() + "\n");
		}

	}

}
