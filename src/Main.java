import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String csvFile = "data/alunos.csv";
        String line = "";
        String csvSplitBy = ";";

        List<Aluno> alunos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Pular a linha do cabeçalho
            while ((line = br.readLine()) != null) {
                String[] alunoData = line.split(csvSplitBy);
                int matricula = Integer.parseInt(alunoData[0]);
                String nome = alunoData[1];
                double nota = Double.parseDouble(alunoData[2].replace(",", "."));
                Aluno aluno = new Aluno(matricula, nome, nota);
                alunos.add(aluno);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        processAndWriteSummary(alunos);
    }

    private static void processAndWriteSummary(List<Aluno> alunos) {
        int totalAlunos = alunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0;

        for (Aluno aluno : alunos) {
            double nota = aluno.getNota();
            somaNotas += nota;
            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double mediaNota = somaNotas / totalAlunos;

        String outputFile = "data/resumo.csv";
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.append("Quantidade de alunos na turma: " + totalAlunos + "\n");
            writer.append("Aprovados: " + aprovados + "\n");
            writer.append("Reprovados: " + reprovados + "\n");
            writer.append("Menor nota: " + menorNota + "\n");
            writer.append("Maior nota: " + maiorNota + "\n");
            writer.append("Média geral: " + mediaNota + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
