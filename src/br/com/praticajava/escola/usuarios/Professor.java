package br.com.praticajava.escola.usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.praticajava.escola.database.ConnectionMySql;
import br.com.praticajava.escola.fabrica.FabricaDeAlunos;

public class Professor extends Usuario {
	
	public Professor(String nome, int senha) {
		this.nome = nome;
		this.senha = senha;
	}
	
	//método simples de confirmação de login do professor para segurança antes de alterar notas no banco de dados
	private void validarLogin() throws Exception {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Digite seu nome");
		String nome = input.next();
		System.out.println("Digite sua senha");
		int senha = input.nextInt();
		
		if(!this.nome.equals(nome) || this.senha != senha) {
			input.close();
			throw new Exception("Login e senha incorretos");
		}
	}
	
	/* retorna uma impressão da base de dados com todos os alunos da turma selecionada, 
	  suas notas e seus status: Aprovado ou Reprovado */
	public void boletimTurma(String turma) {
		ArrayList<Aluno> alunos = FabricaDeAlunos.gerarAlunosPorTurma(turma);

		for (Aluno aluno : alunos) {
			aluno.boletim();
		}
	}
	
	/* altera a nota do trimestre inserido pelo usuário e 
	atualiza as informações da nota, média final e status no banco de dados */
	public void alterarNotaAluno(Aluno aluno) throws Exception {
		validarLogin();
		Connection connection = ConnectionMySql.connect();
		Scanner input = new Scanner(System.in);
		aluno.boletim();

		System.out.println("Selecione o Trimestre que deseja atribuir a nota: ");
		int trimestre = input.nextInt();
		System.out.printf("Tem certeza que deseja alterar o %do Trimestre do " + aluno.getNome() + 
				" ?\n" + "NOTA %do Trimestre: %.2f\n", trimestre,
				trimestre, aluno.getNotas()[trimestre - 1]);
		System.out.println("Digite 1 para continuar");

		int confirm = input.nextInt();
		if (confirm == 1) {
			System.out.println("Digite a nota");
			double nota = input.nextDouble();
			if (nota > 10 || nota < 0) {
				input.close();
				connection.close();
				throw new Exception("Valor inválido, escolha uma nota de 0 a 10");
			}

			switch (trimestre) {
			case 1:
				String sqlCommand = "UPDATE turma01 SET primeira_nota = ? WHERE user_id = ?";
				alteraNotaDb(connection, sqlCommand, nota, aluno);
				break;
			case 2:
				String sqlCommand2 = "UPDATE turma01 SET segunda_nota = ? WHERE user_id = ?";
				alteraNotaDb(connection, sqlCommand2, nota, aluno);
				break;
			case 3:
				String sqlCommand3 = "UPDATE turma01 SET terceira_nota = ? WHERE user_id = ?";
				alteraNotaDb(connection, sqlCommand3, nota, aluno);
				break;
			default:
				input.close();
				throw new IllegalArgumentException("Valor incorreto para trimestre: " + trimestre);
			}
			aluno.isAprovado(connection, trimestre, nota);
		}
		input.close();
		connection.close();
		System.out.println("Encerrado");
	}
	
	//método auxiliar ao alterarNotaAluno(), apenas para evitar repetição de código
	private void alteraNotaDb(Connection connection, String sqlCommand, double nota, Aluno aluno) {
		try {
			PreparedStatement statement = connection.prepareStatement(sqlCommand);
			statement.setDouble(1, nota);
			statement.setInt(2, aluno.getId());

			int executeUpdate = statement.executeUpdate();
			if (executeUpdate > 0) {
				System.out.println("Nota atualizada com sucesso");
			} else {
				System.out.println("Falha ao atualizar a nota");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
