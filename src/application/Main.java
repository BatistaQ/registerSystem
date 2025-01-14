package application;

import entities.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\Kauan\\Desktop\\Projeto Java\\Register System\\form.txt";


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            Scanner sc = new Scanner(System.in);

            String line;
            List<String> dataArray = new ArrayList<>();

            while((line =bufferedReader.readLine()) != null){
                System.out.println(line);
                dataArray.add(validateInput(line,sc));
            }

            String name = dataArray.get(0);
            String email = dataArray.get(1);
            int age = Integer.parseInt(dataArray.get(2));
            double height = Double.parseDouble(dataArray.get(3));

            //Data data = new Data(name,email,age,height);

            Data data = new Data(name,email,age,height);
            ArrayList<Data> datas = new ArrayList<>();
            datas.add(data);


            System.out.println();
            System.out.println(name);
            System.out.println(email);
            System.out.println(age);
            System.out.println(height);

            String formattedName = data.getName().replace(" ","").toUpperCase();
            String outputFile = datas.indexOf(data) + 1 + "-" + formattedName + ".txt";
            System.out.println(outputFile);

            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))){
                bufferedWriter.write("Nome:" + data.getName());
                bufferedWriter.newLine();
                bufferedWriter.write("Email:" + data.getEmail());
                bufferedWriter.newLine();
                bufferedWriter.write("Idade:" + data.getAge());
                bufferedWriter.newLine();
                bufferedWriter.write("Altura:" + data.getHeight());
            } catch (IOException e){
                System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    private static String validateInput(String line, Scanner sc) {
        while(true){
            String input = sc.nextLine();
            if (line.contains("idade")){
                try {
                    Integer.parseInt(input);
                    return input;
                } catch (NumberFormatException e){
                    System.out.println("Por favor, insira um número inteiro válido para idade.");
                }
            } else if (line.contains("altura")){
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
}