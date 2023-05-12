package br.com.praticajava.main;

import java.util.ArrayList;

import br.com.praticajava.escola.fabrica.FabricaDeAlunos;
import br.com.praticajava.escola.usuarios.Aluno;
import br.com.praticajava.escola.usuarios.Professor;

public class Main {

	public static void main(String[] args) throws Exception {
		
//		CrudCommands.inserirAluno("nomeAluno1");
//		CrudCommands.deletarAluno("nomeTurma", 1);

		Professor professor = new Professor("nomeProfessor", 1234);
		
		ArrayList<Aluno> alunos = FabricaDeAlunos.gerarAlunosPorTurma("turma01");
		
		professor.alterarNotaAluno(alunos.get(2));
		
		professor.boletimTurma("turma01");
	}
}

