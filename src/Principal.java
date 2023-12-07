import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario(
                "Maria", LocalDate.parse("18/10/2000", formatter),
                new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(
                new Funcionario("João", LocalDate.parse("12/05/1990", formatter),
                        new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(
                new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter),
                        new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(
                new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter),
                        new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(
                new Funcionario("Alice", LocalDate.parse("05/01/1998", formatter),
                        new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(
                new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter),
                        new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(
                new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter),
                        new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(
                new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter),
                        new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(
                new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter),
                        new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(
                new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter),
                        new BigDecimal("2799.93"), "Gerente"));

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarios.removeIf(funcionario -> funcionario.nome.equals("João"));
        System.out.println("-------------------------------------");

        // Imprimir todos os funcionários com todas suas informações, sendo que:
        //• informação de data deve ser exibido no formato dd/mm/aaaa;
        //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
        imprimirFuncionarios("Lista de Funcionários:", funcionarios, formatter);
        System.out.println("-------------------------------------");

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        aumentarSalarios(funcionarios, new BigDecimal("1.10"));
        imprimirFuncionarios("Lista de Funcionários após aumento de 10% do salário:", funcionarios, formatter);

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        // 3.6 – Imprimir os funcionários, agrupados por função.
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao, formatter);


        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        imprimirAniversariantes(funcionarios, Arrays.asList(Month.OCTOBER, Month.DECEMBER), formatter);

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        imprimirFuncionarioMaiorIdade(funcionarios, formatter);
        System.out.println("-------------------------------------");

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        ordenarFuncionariosPorNome(funcionarios);
        imprimirFuncionarios("Lista de Funcionários em Ordem Alfabética:", funcionarios, formatter);

        // 3.11 – Imprimir o total dos salários dos funcionários.
        imprimirTotalSalarios(funcionarios);

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        imprimirSalariosMinimos(funcionarios, new BigDecimal("1212.00"));
    }

    private static void imprimirFuncionarios(String titulo, List<Funcionario> funcionarios, DateTimeFormatter formatter) {
        System.out.println(titulo);
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.nome);
            System.out.println("Data de Nascimento: " + funcionario.dataNascimento.format(formatter));
            System.out.println("Salário: " + formatarNumero(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        }
    }

    private static void aumentarSalarios(List<Funcionario> funcionarios, BigDecimal percentualAumento) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(percentualAumento);
            funcionario.setSalario(novoSalario);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao, DateTimeFormatter formatter) {
        System.out.println("-------------------------------------");
        System.out.println("Funcionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("**************************");
            System.out.println("Função: " + funcao);
            imprimirFuncionarios("", lista, formatter);
        });
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, List<Month> meses, DateTimeFormatter formatter) {
        System.out.println("-------------------------------------");
        System.out.println("Aniversariantes nos Meses 10 e 12:");
        for (Funcionario funcionario : funcionarios) {
            if (meses.contains(funcionario.dataNascimento.getMonth())) {
                System.out.println("Nome: " + funcionario.nome);
                System.out.println("Data de Nascimento: " + funcionario.dataNascimento.format(formatter));
                System.out.println();
            }
        }
    }

    private static void imprimirFuncionarioMaiorIdade(List<Funcionario> funcionarios, DateTimeFormatter formatter) {
        System.out.println("-------------------------------------");
        System.out.println("Funcionário com Maior Idade:");

        Funcionario maiorIdade = Collections.max(funcionarios, Comparator.comparing(Funcionario::getDataNascimento).reversed());

        long idade = LocalDate.now().getYear() - maiorIdade.getDataNascimento().getYear();
        System.out.println("Nome: " + maiorIdade.getNome());
        System.out.println("Idade: " + idade + " anos");
        System.out.println();
    }


    private static void ordenarFuncionariosPorNome(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(funcionario -> funcionario.nome));
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        System.out.println("-------------------------------------");
        System.out.println("Total dos Salários dos Funcionários: " + formatarNumero(
                funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add)));
        System.out.println();
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        System.out.println("-------------------------------------");

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);

            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Salários Mínimos: " + formatarNumero(salariosMinimos));
            System.out.println();
        }
    }


    private static String formatarNumero(BigDecimal numero) {
        return String.format("%,.2f", numero);
    }
}