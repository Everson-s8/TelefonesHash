package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BuscaTelefone {

    public static void buscarENotificar(String nomeBusca, String cpfBusca, Map<String, List<String>> agendaTelefonica, Map<String, Pessoa> pessoas) throws Exception {
        List<String> numerosEncontrados = buscarNumeros(nomeBusca, agendaTelefonica, pessoas);

        if (numerosEncontrados.isEmpty()) {
            throw new Exception("Nenhum número encontrado para o nome: " + nomeBusca);
        } else if (numerosEncontrados.size() == 1) {
            System.out.println("Número encontrado: " + numerosEncontrados.get(0));
        } else {
            System.out.println("Múltiplos números encontrados para o nome: " + nomeBusca);

            // Verificar se foi fornecido um CPF
            if (cpfBusca != null && !cpfBusca.isEmpty()) {
                // Buscar especificamente pelo CPF fornecido
                List<String> numerosEspecificos = buscarNumerosPorCpf(cpfBusca, agendaTelefonica, pessoas);

                if (numerosEspecificos.isEmpty()) {
                    throw new Exception("Nenhum número encontrado para o CPF: " + cpfBusca);
                } else {
                    System.out.println("Números encontrados para o CPF " + cpfBusca + ": " + numerosEspecificos);
                }
            } else {
                // Verificar se há apenas um CPF associado ao nome
                List<String> cpfsAssociados = encontrarCpfsAssociados(nomeBusca, agendaTelefonica, pessoas);

                if (cpfsAssociados.size() == 1) {
                    // Buscar diretamente pelo CPF único
                    List<String> numerosEspecificos = buscarNumerosPorCpf(cpfsAssociados.get(0), agendaTelefonica, pessoas);
                    System.out.println("Números encontrados para o CPF " + cpfsAssociados.get(0) + ": " + numerosEspecificos);
                } else {
                    // Solicitar o CPF para uma busca mais específica
                    Scanner scanner = new Scanner(System.in);
                    String cpfDigitado = obterCpfValido(scanner, cpfsAssociados);

                    // Buscar especificamente pelo CPF fornecido
                    List<String> numerosEspecificos = buscarNumerosPorCpf(cpfDigitado, agendaTelefonica, pessoas);

                    if (numerosEspecificos.isEmpty()) {
                        throw new Exception("Nenhum número encontrado para o CPF: " + cpfDigitado);
                    } else {
                        System.out.println("Números encontrados para o CPF " + cpfDigitado + ": " + numerosEspecificos);
                    }
                }
            }
        }
    }

    private static String obterCpfValido(Scanner scanner, List<String> cpfsAssociados) throws Exception {
        String cpfDigitado;
        do {
            System.out.print("Favor fornecer um CPF válido associado ao nome: ");
            cpfDigitado = scanner.nextLine();
        } while (!cpfsAssociados.contains(cpfDigitado));
        return cpfDigitado;
    }

    private static List<String> encontrarCpfsAssociados(String nome, Map<String, List<String>> agenda, Map<String, Pessoa> pessoas) throws Exception {
        List<String> cpfsAssociados = new ArrayList<>();
        for (Map.Entry<String, Pessoa> entry : pessoas.entrySet()) {
            Pessoa pessoa = entry.getValue();
            if (pessoa.getNome().equals(nome)) {
                cpfsAssociados.add(pessoa.getCpf());
            }
        }
        return cpfsAssociados;
    }

    private static List<String> buscarNumerosPorCpf(String cpf, Map<String, List<String>> agenda, Map<String, Pessoa> pessoas) throws Exception {
        if (agenda.containsKey(cpf)) { // verificar se o cpf tem algum numero associado
            return agenda.get(cpf);
        } else {
            throw new Exception("Nenhum número encontrado para o CPF: " + cpf);
        }
    }

    private static List<String> buscarNumeros(String nome, Map<String, List<String>> agenda, Map<String, Pessoa> pessoas) throws Exception {
        List<String> resultados = new ArrayList<>(); // cria uma lista para armazenar os resultados
        for (Map.Entry<String, Pessoa> entry : pessoas.entrySet()) {
            Pessoa pessoa = entry.getValue(); // obtem a instancia de pessoa atual do loop
            if (pessoa.getNome().equals(nome)) { // verifica se o nome atual é igual o desejado
                String cpfPessoa = pessoa.getCpf();
                if (agenda.containsKey(cpfPessoa)) { // verifica se o cpf está associado igual desejado
                    resultados.addAll(agenda.get(cpfPessoa)); // adiciona o valor se estiver correto a resultados
                }
            }
        }
        return resultados; // retorna a lista de todos o números associados a pessoa que desejava buscar
    }

    public static void adicionarNumero(String numero, String nome, String cpf,
                                       Map<String, List<String>> agenda, Map<String, Pessoa> pessoas) throws Exception {
        if (existeNumeroRepetido(numero, agenda)) { // verifica se o número já existe registrado a outra pessoa
            throw new Exception("Erro: Número de telefone repetido.");
        }

        if (pessoas.containsKey(cpf)) { // Verificar se o mapa pessoas já contem uma entrada com chave cpf, se já existe alguem com pessoas com cpf registrado
            if (pessoas.get(cpf).getNome().equals(nome)) { //Verifica se não existe o numero associado a algum cpf
                if (!agenda.get(cpf).contains(numero)) { // verifica se o cpf está associado ao nome certo
                    agenda.get(cpf).add(numero);  // Se tudo estiver correto adiciona ao cpf o novo número
                } else {
                    throw new Exception("Erro: Número de telefone repetido para a mesma pessoa.");
                }
            } else {
                throw new Exception("Erro: CPF já associado a outro nome.");
            }
        } else {  // Se o cpf não já tiver registrado em pessoas
            List<String> numeros = new ArrayList<>(); // cria uma lista de numeros
            numeros.add(numero); // adiciona o número
            agenda.put(cpf, numeros); // associa o número ao cpf na agenda
            pessoas.put(cpf, new Pessoa(nome, cpf)); // associa o cpf a um novo objeto de pessoas
        }
    }

    private static boolean existeNumeroRepetido(String numero, Map<String, List<String>> agenda) { // Faz a verificacao se existe o numero, a outra pessoa
        for (List<String> numeros : agenda.values()) {
            if (numeros.contains(numero)) {
                return true;
            }
        }
        return false;
    }
}
