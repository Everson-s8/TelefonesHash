package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws Exception {
        // Criar uma tabela hash para armazenar os números de telefone
        Map<String, List<String>> agendaTelefonica = new HashMap<>();
        Map<String, Pessoa> pessoas = new HashMap<>();
        BuscaTelefone buscaTelefone = new BuscaTelefone();

        // Adicionar alguns números de exemplo à tabela hash
        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("987456321", "Maria", "222.333.444-55", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("111223344", "Carlos", "333.444.555-66", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("555555555", "João", "444.555.666-77", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("79998253860","Everson","400.002.892-26", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("79999655428","Everson","400.002.892-26", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("987654321", "João", "111.222.333-44", agendaTelefonica, pessoas);


        // Adicionar uma nova pessoa com número
        buscaTelefone.adicionarNumero("555666777", "Pedro", "999.888.777-66", agendaTelefonica, pessoas);

        // Tentar adicionar um número repetido para João (deve exibir mensagem de erro)
//        buscaTelefone.adicionarNumero("555555555", "João", "444.555.666-77", agendaTelefonica, pessoas);
        // Cpf já associado erro
//        buscaTelefone.adicionarNumero("79999655428","Eliene","400.002.892-26", agendaTelefonica, pessoas);


        // Realizar buscas
        buscaTelefone.buscarENotificar("Everson", null, agendaTelefonica,pessoas);
        buscaTelefone.buscarENotificar("Maria", null, agendaTelefonica,pessoas);
        buscaTelefone.buscarENotificar("João", null, agendaTelefonica,pessoas);
    }
}