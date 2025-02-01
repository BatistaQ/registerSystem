package application;

import entities.Data;
import entities.exceptions.InvalidAgeException;
import entities.exceptions.InvalidEmailException;
import entities.exceptions.InvalidHeightException;
import entities.exceptions.InvalidNameException;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

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
            System.out.print("Choose an option: ");

            int option = getValidInteger(sc);
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

    private static int getValidInteger(Scanner sc) {
        while(!sc.hasNextInt()){
            System.out.println("Invalid input.");
            sc.next();
        }
        return sc.nextInt();
    }

    private static List<String> registeredEmails = new ArrayList<>();
    private static void registerUser(Scanner sc) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(outputPath))){

            String line;
            List<String> dataArray = new ArrayList<>();

            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
                dataArray.add(validateInput(line,sc)); //chama a validação dos dados antes de adicionar ao array
            }

            String name = dataArray.get(0);
            String email = dataArray.get(1);
            int age = Integer.parseInt(dataArray.get(2));
            double height = Double.parseDouble(dataArray.get(3));

            Data data = new Data(name,email,age,height);
            dataList.add(data);
            registeredEmails.add(email);
            writeFile(data);

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static String validateInput(String line, Scanner sc) {
        while(true){
            String input = sc.nextLine();

            if (line.contains("seu nome")){
                validateName(input);
            }
            else if (line.contains("email")){
                validateEmail(input);
            }
            else if (line.contains("idade")){
                validateAge(input);
            }
            else if (line.contains("altura")){
                validateHeight(input);
            }

            return input;
        }
    }

    private static void validateName(String input) throws InvalidNameException {
        if(input.replaceAll("\\s+","").length() < 10){
            throw new InvalidNameException("O nome deve ter pelo menos 10 caracteres.");
        }
    }


    private static void validateEmail(String input) throws InvalidEmailException {
        if (!input.contains("@")){
            throw new InvalidEmailException("O email deve conter @");
        }

        if (registeredEmails.contains(input)){
            throw new InvalidEmailException("O email já foi cadastrado.");
        }
    }

    private static void validateAge(String input) throws InvalidAgeException {
        try {
            int age = Integer.parseInt(input);
            if (age < 18){
                throw new InvalidAgeException("Usuario deve ter no minimo 18 anos.");
            }
        } catch (InputMismatchException e){
            System.out.println("Insira um número válido para idade.");
        }
    }

    private static void validateHeight(String input) throws InvalidHeightException{
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e){
            System.out.println("Insira um número válido para altura.");
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