package com.erlds.projedata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    private List<Funcionario> funcionarios;

    public Principal() {
        this.funcionarios = criaListaDeFuncionarios();
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    private List<Funcionario> criaListaDeFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    public boolean removeFuncionarioPeloNome(String name) {
        return funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(name));
    }

    public void imprimeInformacaoDeTodosOsFuncionarios() {
        System.out.println("Informações de funcionários:\n");
        funcionarios.forEach(System.out::println);
        System.out.println("\n");
    }

    public void atualizaSalarioDeFuncionarios(double multiplicador) {
        BigDecimal multiplicadorBigDecimal = new BigDecimal(multiplicador);
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(multiplicadorBigDecimal)));
    }

    public Map<String,List<Funcionario>> agrupaFuncionariosByFuncao() {
        Map<String,List<Funcionario>> funcionariosMap = new HashMap<>();
        List<String> funcoes = funcionarios.stream().map(f -> f.getFuncao()).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
        funcoes.forEach(funcao -> {
            List<Funcionario> filtered = funcionarios.stream().filter(f -> f.getFuncao().equals(funcao)).collect(Collectors.toList());
            funcionariosMap.put(funcao, filtered);
        });
        return funcionariosMap;
    }

    public void imprimeFunctionariosAgrupadosPorFuncao() {
        System.out.println("Funcionarios agrupados por funcão:\n");
        System.out.println(agrupaFuncionariosByFuncao());
        System.out.println("\n");
    }

    public void imprimeFunctionariosDoMesDeAniversario(int mes) {
        List<Funcionario> funcionariosDoMesDeterminado = funcionarios.stream().filter(f -> f.getDataNascimento().getMonthValue() == mes).collect(Collectors.toList());
        if (funcionariosDoMesDeterminado.isEmpty()) {
            System.out.println("Não existem funcionários fazendo aniversario no mês " + mes + "\n");
        } else {
            System.out.println("Funcionarios que fazem aniversario no mês " + mes + ":\n");
            funcionariosDoMesDeterminado.forEach(System.out::println);
        }
    }

    public void imprimeFuncionarioComAMaiorIdade() {
        Funcionario funcionario = funcionarios.stream()
            .min(Comparator.comparing(Funcionario::getDataNascimento))
            .orElse(null);
        
        LocalDate agora = LocalDate.now();

        Period periodo = Period.between(funcionario.getDataNascimento(), agora);
        int idade = periodo.getYears();
        System.out.println("\nFuncionario(a) mais antigo da empresa: " + funcionario.getNome() + " com " + idade + " anos de idade\n");
    }

    public void imprimeFuncionariosEmOrdemAlfabetica() {
        System.out.println("Funcionarios em ordem alfabetica:");
        List<Funcionario> sortedFuncionarios = funcionarios.stream()
                .sorted().collect(Collectors.toList());
        System.out.println(sortedFuncionarios);
    }

    public void imprimeTotalDosSalariosDosFuncionarios() {
        BigDecimal totalSalario = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        System.out.println("\nTotal dos salários dos funcionários: " + totalSalario + "\n");
    }

    public void imprimeQuantidadeDeSalariosMinimosDeCadaFuncionario() {
        BigDecimal salarioMinimo = new BigDecimal(1212.00);
        System.out.println("Salários mínimos recebidos por funcionario ! ");
        System.out.println("-------------------------------------------------------");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo,2, RoundingMode.HALF_UP);
            System.out.println("Funcionário(a) " + f.getNome() + " recebe " + salariosMinimos + " salários mínimos");
        });
        System.out.println("-------------------------------------------------------");
    }

    public static void main(String args[]) {
        Principal principal = new Principal();

        principal.removeFuncionarioPeloNome("João");
        
        principal.imprimeInformacaoDeTodosOsFuncionarios();

        principal.atualizaSalarioDeFuncionarios(1.1);

        principal.imprimeFunctionariosAgrupadosPorFuncao();

        principal.imprimeFunctionariosDoMesDeAniversario(10);
        
        principal.imprimeFunctionariosDoMesDeAniversario(12);
        
        principal.imprimeFuncionarioComAMaiorIdade();
        
        principal.imprimeFuncionariosEmOrdemAlfabetica();
        
        principal.imprimeTotalDosSalariosDosFuncionarios();
        
        principal.imprimeQuantidadeDeSalariosMinimosDeCadaFuncionario();
    }
}