package application;

import entities.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int sum = 1;
    private static String outputPath = "C:\\Users\\Kauan\\Desktop\\Projeto Java\\Register System\\form.txt";
    private static ArrayList<Data> dataList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Menu: ");
            System.out.println("1 - Register User");
            System.out.println("2 - List all registered users");
            System.out.println("3 - Register new question in the form");
            System.out.println("4 - Delete question from form");
            System.out.println("5 - Search user by name, email or age");
            System.out.println();

            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine();
            
            switch (option){
                case 1:
                    registerUser(sc);
                    break;
                case 2:
                    listAll();
                    break;
                case 3:
                    newQuestion(sc);
                    break;
                case 4:
                    deleteQuestion(sc);
                    break;
                case 5:
                    searchUser(sc);
                    break;
            }
        }
    }

    private static void registerUser(Scanner sc) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(outputPath))){

            String line;
            List<String> dataArray = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
                dataArray.add(validateInput(line,sc));
            }

            String name = dataArray.get(0);
            String email = dataArray.get(1);
            int age = Integer.parseInt(dataArray.get(2));
            double height = Double.parseDouble(dataArray.get(3));

            Data data = new Data(name,email,age,height);
            dataList.add(data);

            System.out.println();
            System.out.println(name);
            System.out.println(email);
            System.out.println(age);
            System.out.println(height);

            System.out.println();

            writeFile(data);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static String validateInput(String line, Scanner sc) {
        while(true){
            String input = sc.nextLine();
            if (line.contains("sua idade")){
                try {
                    Integer.parseInt(input);
                    return input;
                } catch (NumberFormatException e){
                    System.out.println("Por favor, insira um número inteiro válido para idade.");
                }
            } else if (line.contains("sua altura")){
                try {
                    Double.parseDouble(input);
                    return input;
                } catch (NumberFormatException e){
                    System.out.println("Por favor, insira um número decimal válido para altura.");
                }
            } else {
                return input;
            }
        }
    }

    private static void writeFile(Data data) {

        String formattedName = data.getName().replace(" ","").toUpperCase();
        String outputFile = sum + "-" + formattedName + ".txt";

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))){
            bufferedWriter.write("Nome: " + data.getName());
            bufferedWriter.newLine();
            bufferedWriter.write("Email: " + data.getEmail());
            bufferedWriter.newLine();
            bufferedWriter.write("Idade: " + data.getAge());
            bufferedWriter.newLine();
            bufferedWriter.write("Altura: " + data.getHeight());

            System.out.println("User saved successfully!");
            System.out.println();
        } catch (IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
        sum++;
    }

    private static void listAll() {
        if (dataList.isEmpty()){
            System.out.println("No registered users");
            System.out.println();
            return;
        }

        System.out.println("Registered users:");
        for (int i = 0; i < dataList.size(); i++) {
            System.out.println(i + 1 + " - " + dataList.get(i).getName());
        }
        System.out.println();
    }

    private static void newQuestion(Scanner sc) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath,true))){
            System.out.print("Digite a nova pergunta: ");
            String question = sc.nextLine();

            bufferedWriter.write(question);
            bufferedWriter.newLine();

            System.out.println("Pergunta adicionada com sucesso!");
            System.out.println();
        } catch (IOException e){
            System.out.println("Erro ao adicionar a pergunta: " + e.getMessage());
        }
    }

    private static void deleteQuestion(Scanner sc) {
        List<String> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(outputPath))){
            String line;
            while((line = br.readLine()) != null){
                questions.add(line);
            }
        } catch (IOException e){
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }
        System.out.println("Perguntas que podem ser removidas:");
        for (int i = 4; i < questions.size(); i++){
            System.out.println(questions.get(i));
        }
        System.out.println();

        System.out.print("Escolha o número da pergunta que deseja excluir: ");

        //Verificar entrada
        if (!sc.hasNextInt()){
            System.out.println("Entrada inválida. Digite um número válido");
            sc.nextLine();
            return;
        }

        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println();

        if (choice < 5 || choice > questions.size()){
            System.out.println("Opção inválida. Delete a partir da pergunta numero 5.");
            return;
        }

        questions.remove(choice - 1);
        System.out.println("Pergunta removida com sucesso!");


        try(BufferedWriter bf = new BufferedWriter(new FileWriter(outputPath))){
            for(String question : questions){
                bf.write(question);
                bf.newLine();
            }
        } catch (IOException e){
            System.out.println("Erro ao escrever arquivo: " + e.getMessage());
        }

        System.out.println();
    }

    private static void searchUser(Scanner sc) {
        System.out.print("Digite o termo para buscar (nome,email ou idade): ");
        String searchTerm = sc.nextLine().toLowerCase();

        List<Data> matchedUsers = new ArrayList<>();

        for (Data user : dataList){
            //Separa o nome para verificar posteriormente
            String[] nameParts = user.getName().toLowerCase().split(" ");

            boolean matches = false;

            for (String part : nameParts){
                if (part.startsWith(searchTerm)) {
                    matches = true;
                    break;
                }
            }

            if (matches ||
                user.getEmail().toLowerCase().contains(searchTerm) ||
                String.valueOf(user.getAge()).contains(searchTerm)){

                matchedUsers.add(user);
            }
        }

        if (matchedUsers.isEmpty()){
            System.out.println("Nenhum usuário encontrado.");
            System.out.println();
        } else {
            matchedUsers.sort(Comparator.comparing(Data::getName)); //Ordernar por A-Z
            System.out.println("Usuários encontrados:");

            for (Data user : matchedUsers){
                System.out.println("Nome: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Idade: " + user.getAge());
                System.out.println("Altura: " + user.getHeight());
                System.out.println();
            }
            System.out.println();
        }
    }
}