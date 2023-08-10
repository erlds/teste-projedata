package com.erlds.projedata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrincipalTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Principal principal;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        principal = new Principal();
    }

    @Test
    public void testRemoveUsuarioPeloNome() {
        assertTrue(principal.removeFuncionarioPeloNome("João"));
    }

    @Test
    public void testRemoveUsuarioPeloNomeNaoExistente() {
        assertFalse(principal.removeFuncionarioPeloNome("José"));
    }

    @Test
    public void testImprimeInformacaoDeTodosOsFuncionarios() {
        principal.imprimeInformacaoDeTodosOsFuncionarios();

        String mensagemGerada = outputStreamCaptor.toString().trim();
        List<String> nomes = extraiNomes(mensagemGerada);
        assertTrue(oNomeEhOPrimeiro(nomes, "Maria"));
    }

    @Test
    public void testAtualizaSalarioDeFuncionarios() {
        principal.atualizaSalarioDeFuncionarios(1.1);

        assertEquals(principal.getFuncionarios().get(0).getSalario().doubleValue(),2210.384);
    }

    @Test
    public void testAgrupaFuncionariosByFuncao() {
        Map<String,List<Funcionario>> funcionariosPorFuncao = principal.agrupaFuncionariosByFuncao();
        assertEquals(funcionariosPorFuncao.get("Diretor").get(0).getNome(),"Miguel");
    }

    @Test
    public void testImprimeFunctionariosDoMesDeAniversario12() {
        int mes = 12;
        principal.imprimeFunctionariosDoMesDeAniversario(mes);

        String mensagemGerada = outputStreamCaptor.toString().trim();
        String mensagemEsperada = "Não existem funcionários fazendo aniversario no mês " + mes;
        
        assertEquals(mensagemEsperada,mensagemGerada);
    }

    @Test
    public void testImprimeFunctionariosDoMesDeAniversario10() {
        int mes = 10;
        principal.imprimeFunctionariosDoMesDeAniversario(mes);

        String mensagemGerada = outputStreamCaptor.toString().trim();
        List<String> nomes = extraiNomes(mensagemGerada);
        
        assertTrue(oNomeEhOPrimeiro(nomes, "Maria"));        
    }

    @Test
    public void testImprimeFuncionarioComAMaiorIdade() {
        principal.imprimeFuncionarioComAMaiorIdade();

        String mensagemGerada = outputStreamCaptor.toString().trim();
        String mensagemEsperada = "Funcionario(a) mais antigo da empresa: Caio com 62 anos de idade";
        
        assertEquals(mensagemEsperada,mensagemGerada);
    }

    @Test
    public void testImprimeFuncionariosEmOrdemAlfabetica() {
        principal.imprimeFuncionariosEmOrdemAlfabetica();
        
        String mensagemGerada = outputStreamCaptor.toString().trim();
        List<String> nomes = extraiNomes(mensagemGerada);
        
        assertTrue(oNomeEhOPrimeiro(nomes, "Alice"));
    }

    @Test
    public void testImprimeTotalDosSalariosDosFuncionarios() {
        principal.imprimeTotalDosSalariosDosFuncionarios();
        
        String mensagemGerada = outputStreamCaptor.toString().trim();
        String mensagemEsperada = "Total dos salários dos funcionários: 48563.31";
        
        assertEquals(mensagemEsperada, mensagemGerada);
    }

    @Test
    public void testImprimeQuantidadeDeSalariosMinimosDeCadaFuncionario() {
        principal.imprimeQuantidadeDeSalariosMinimosDeCadaFuncionario();;

        String mensagemGerada = outputStreamCaptor.toString().trim();
        String mensagemParaFuncionariaMaria = "Funcionário(a) Maria recebe 1.66 salários mínimos";
        
        assertTrue(mensagemGerada.contains(mensagemParaFuncionariaMaria));
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    private List<String> extraiNomes(String input) {
        List<String> nomes = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("Nome: (.+)");
        Matcher matcher = pattern.matcher(input);
        
        while (matcher.find()) {
            nomes.add(matcher.group(1));
        }
        
        return nomes;
    }

    private boolean oNomeEhOPrimeiro(List<String> nomes, String nome) {
        return nomes.size() > 0 && nomes.get(0).equals(nome);
    }
}
