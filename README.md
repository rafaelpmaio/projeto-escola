# projeto-escola
Projeto feito para treinar os comandos CRUD no mySql utilizando Java

A base de dados foi feita para armazenar as informações dos alunos:

CREATE TABLE `database_escola`.`turma1` (`user_id` INT NOT NULL AUTO_INCREMENT , `nome` VARCHAR(80) NOT NULL , 
`primeira_nota` FLOAT NULL , `segunda_nota` FLOAT NULL , `terceira_nota` FLOAT NULL , `media_final` FLOAT NULL , 
`status` ENUM('APROVADO', 'REPROVADO') NULL , PRIMARY KEY (`user_id`)) ENGINE = InnoDB;


O projeto gira em torno de 3 classes principais:
FabricaDeAlunos -> gera uma lista<Aluno> puxando as informações do banco de dados;
  
Aluno -> com atributos: nome, notas[3], mediaNotas, status(Aprovado ou Reprovado), além do método boletim() que imprime 
  todas as informações citadas e outros métodos auxiliares; 
  
Professor -> consegue puxar boletim() de todos os alunos de determinada turma através do boletimTurma() e alterar nota
  do trimestre desejado para o aluno através do alterarNotaAluno();
  
obs: os comandos para inserir e deletar aluno da base de dados foram criados mas não atribuidos para nenhuma das
  classes acima citadas, estão presentes na classe CrudCommands; demais métodos estão com comentádos.
  

