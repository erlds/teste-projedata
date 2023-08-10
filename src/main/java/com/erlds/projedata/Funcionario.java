package com.erlds.projedata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Funcionario extends Pessoa implements Comparable<Funcionario>{
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDataNascimento = getDataNascimento().format(dateFormatter);
        String formattedSalario = String.format("%.2f", salario);

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("------------------------------\n");
        builder.append("Nome: ").append(getNome()).append("\n");
        builder.append("Data de Nascimento: ").append(formattedDataNascimento).append("\n");
        builder.append("Salário: ").append(formattedSalario).append("\n");
        builder.append("Função: ").append(funcao).append("\n");
        builder.append("------------------------------\n");

        return builder.toString();
    }

    @Override
    public int compareTo(Funcionario outroFuncionario) {
        return this.getNome().compareTo(outroFuncionario.getNome());
    }
}
