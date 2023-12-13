import org.example.Pessoa;
import org.example.BuscaTelefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuscaTelefoneTest {
    private BuscaTelefone buscaTelefone;
    private Map<String, List<String>> agendaTelefonica;
    private Map<String, Pessoa> pessoas;

    @BeforeEach
    public void setUp() {
        buscaTelefone = new BuscaTelefone();
        agendaTelefonica = new HashMap<>();
        pessoas = new HashMap<>();
    }

    @Test
    public void testNewNumero() throws Exception {
        // antes de inserir
        assertThat(agendaTelefonica.isEmpty()).isEqualTo(true);
        // Inserindo novo número
        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);

        // depois de inserir o valor espera que agenda telefonica tenha o número
        assertThat(agendaTelefonica.isEmpty()).isEqualTo(false);
        assertTrue(agendaTelefonica.containsKey("111.222.333-44"));
    }

    // Testar o error de tentar associar o cpf já associado a outro nome
    @Test
    public void errorCpfRepetido() throws Exception {
        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);

        Exception exception = assertThrows(Exception.class, () -> {
            buscaTelefone.adicionarNumero("987652131", "Maria", "111.222.333-44", agendaTelefonica, pessoas);
        });
        assertThat(exception.getMessage()).isEqualTo("Erro: CPF já associado a outro nome.");
    }

    // Testar o error de tentar associar um número associado a outra pessoa já
    @Test
    public void errorNumeroRepetido() throws Exception {
        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);

        Exception exception = assertThrows(Exception.class, () -> {
            buscaTelefone.adicionarNumero("123456789", "Maria", "222.333.444-55", agendaTelefonica, pessoas);
        });
        assertThat(exception.getMessage()).isEqualTo("Erro: Número de telefone repetido.");
    }

    // Tentar buscar pessoa pelo nome errado
    @Test
    public void errorPessoaErrada() throws Exception {
        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);

        Exception exception = assertThrows(Exception.class, () -> {
            buscaTelefone.buscarENotificar("Maria", null, agendaTelefonica, pessoas);
        });
        assertThat(exception.getMessage()).isEqualTo("Nenhum número encontrado para o nome: Maria");
    }

    //    Múltiplos números encontrados para o nome: João
    // Test em buscar por apenas nome quando se tem nomes repetidos
    // O codigo está seguindo conceito para busca mais rapida de pessoas usar o nome da pessoa
    // mas pode ocorrer de ter dois nomes iguais, nessa ocasiação vc acaba passando o cpf especifico da pessoa que se procura
    // abrindo um scanner só nesse momento para pessoa , quando acontecer esse fato
    @Test
    public void pessoasComNomesIguais() throws Exception {

        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("555555555", "João", "444.555.666-77", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("987654321", "João", "111.222.333-44", agendaTelefonica, pessoas);

        ByteArrayInputStream entradaUsuario = new ByteArrayInputStream("111.222.333-44\n".getBytes());
        System.setIn(entradaUsuario);
        buscaTelefone.buscarENotificar("João", null, agendaTelefonica, pessoas);

    }

    // Buscar e adicionar multiplos usuarios
    @Test
    public void multiplosUsuarios() throws Exception {

        buscaTelefone.adicionarNumero("123456789", "João", "111.222.333-44", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("987456321", "Maria", "222.333.444-55", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("111223344", "Carlos", "333.444.555-66", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("555555555", "Pedro", "444.555.666-77", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("79998253860", "Everson", "400.002.892-26", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("79999655428", "Everson", "400.002.892-26", agendaTelefonica, pessoas);
        buscaTelefone.adicionarNumero("987654321", "João", "111.222.333-44", agendaTelefonica, pessoas);

        System.out.println();
        // Realizar buscas
        buscaTelefone.buscarENotificar("Everson", null, agendaTelefonica, pessoas);
        System.out.println("Numero de Maria");
        buscaTelefone.buscarENotificar("Maria", null, agendaTelefonica, pessoas);
        buscaTelefone.buscarENotificar("João", null, agendaTelefonica, pessoas);
        System.out.println("Numero de Pedro");
        buscaTelefone.buscarENotificar("Pedro", null, agendaTelefonica, pessoas);
        System.out.println("Numero de Carlos");
        buscaTelefone.buscarENotificar("Carlos", null, agendaTelefonica, pessoas);


    }
}
