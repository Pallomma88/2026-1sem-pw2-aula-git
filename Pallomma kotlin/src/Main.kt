// ===================================================================
// BLOCO 1 — AQUECIMENTO (Classes, Encapsulamento e Métodos)
// ===================================================================

open class Produto(
    val nome: String,
    private var preco: Double,
    var quantidadeEstoque: Int
) {

    fun getPreco(): Double {
        return preco
    }

    fun setPreco(valor: Double) {
        if (valor < 0) {
            println("Erro: o preço não pode ser negativo.")
        } else {
            preco = valor
        }
    }

    // CORREÇÃO: Formatação simplificada para evitar erros no console
    fun imprimir() {
        val precoFormatado = "%.2f".format(getPreco())
        println("Produto: $nome | Preço: R$ $precoFormatado | Estoque: $quantidadeEstoque")
    }

    // CORREÇÃO: Ajustado o .format() para não dar erro de compilação
    fun aplicarDesconto(percentual: Double) {
        if (percentual < 0 || percentual > 100) {
            println("Erro: percentual de desconto inválido ($percentual%). Deve estar entre 0 e 100.")
            return
        }
        val novoPreco = getPreco() - (getPreco() * percentual / 100)
        setPreco(novoPreco)

        val precoFormatado = "%.2f".format(getPreco())
        println("Desconto de $percentual% aplicado em $nome. Novo preço: R$ $precoFormatado")
    }
}

// ===================================================================
// BLOCO 2 — INTERMEDIÁRIO (Herança)
// ===================================================================

class ProdutoPerecivel(
    nome: String,
    preco: Double,
    quantidadeEstoque: Int,
    val dataValidade: String
) : Produto(nome, preco, quantidadeEstoque) {

    fun estaVencido(dataHoje: String): Boolean {
        return dataValidade < dataHoje
    }
}

// ===================================================================
// BLOCO 3 — AVANÇADO (Polimorfismo e Classes Abstratas)
// ===================================================================

abstract class FuncionarioBase(
    val nome: String,
    val salarioBase: Double
) {
    abstract fun calcularSalario(): Double
}

class Vendedor(
    nome: String,
    salarioBase: Double,
    var totalVendas: Double = 0.0
) : FuncionarioBase(nome, salarioBase) {

    override fun calcularSalario(): Double {
        return salarioBase + (totalVendas * 0.05)
    }
}

class Gerente(
    nome: String,
    salarioBase: Double,
    val bonusFixo: Double
) : FuncionarioBase(nome, salarioBase) {

    override fun calcularSalario(): Double {
        return salarioBase + bonusFixo
    }
}

fun imprimirFolhaPagamento(funcionarios: List<FuncionarioBase>) {
    var totalFolha = 0.0

    for (funcionario in funcionarios) {
        val tipo = when (funcionario) {
            is Vendedor -> "Vendedor"
            is Gerente -> "Gerente"
            else -> "Funcionário"
        }
        val salario = funcionario.calcularSalario()
        val salarioFormatado = "%.2f".format(salario)
        println("${funcionario.nome} ($tipo) -> R$ $salarioFormatado")
        totalFolha += salario
    }

    println("---")
    println("Total da folha: R$ %.2f".format(totalFolha))
}

// ===================================================================
// BLOCO 4 — DESAFIO FINAL (Simulação do Sistema)
// ===================================================================

fun finalizarVenda(carrinho: List<Produto>, vendedor: Vendedor, dataHoje: String) {
    var totalVenda = 0.0

    println("===== Finalizando venda =====")

    for (produto in carrinho) {
        if (produto is ProdutoPerecivel && produto.estaVencido(dataHoje)) {
            println("Aviso: o produto ${produto.nome} está VENCIDO! (validade: ${produto.dataValidade})")
        }

        val precoFormatado = "%.2f".format(produto.getPreco())
        println("Item: ${produto.nome} | Preço: R$ $precoFormatado")

        totalVenda += produto.getPreco()
        produto.quantidadeEstoque -= 1
    }

    vendedor.totalVendas += totalVenda

    val totalVendaFormatado = "%.2f".format(totalVenda)
    val salarioVendedorFormatado = "%.2f".format(vendedor.calcularSalario())

    println("Total da venda: R$ $totalVendaFormatado")
    println("Salário atualizado de ${vendedor.nome}: R$ $salarioVendedorFormatado")
}

// ===================================================================
// EXECUÇÃO DO PROGRAMA (Apenas um único main aqui embaixo!)
// ===================================================================

fun main() {
    // Se quiser ver o emoji, ele pode ficar bem aqui no começo do seu único main!
    val kotlin = "🙂"
    println(kotlin)

    println("===== BLOCO 1 — Testando Produtos =====")
    val arroz = Produto("Arroz", 8.50, 100)
    val feijao = Produto("Feijão", 7.00, 50)
    val macarrao = Produto("Macarrão", 4.50, 200)

    arroz.imprimir()
    feijao.imprimir()

    println("\n--- Testando Descontos ---")
    arroz.aplicarDesconto(10.0)
    feijao.aplicarDesconto(-5.0)

    println("\n--- Testando Encapsulamento ---")
    arroz.setPreco(-20.0)
    arroz.setPreco(9.00)
    arroz.imprimir()

    println("\n===== BLOCO 2 — Testando Produto Perecível =====")
    val leite = ProdutoPerecivel("Leite", 5.00, 30, "2026/07/05")
    val iogurte = ProdutoPerecivel("Iogurte", 3.50, 40, "2026/06/20")

    val hoje = "2026/06/30"
    println("${leite.nome} está vencido? ${leite.estaVencido(hoje)}")
    println("${iogurte.nome} está vencido? ${iogurte.estaVencido(hoje)}")

    println("\n===== BLOCO 3 — Funcionários e Folha =====")
    val joao = Vendedor("João", 1500.0, totalVendas = 35000.0)
    val maria = Gerente("Maria", 5000.0, bonusFixo = 1000.0)

    val funcionarios = listOf(joao, maria)
    imprimirFolhaPagamento(funcionarios)

    println("\n===== BLOCO 4 — Executando uma Venda (Desafio) =====")
    val carrinho = listOf(arroz, feijao, leite, iogurte)
    val vendedorPedro = Vendedor("Pedro", 1500.0)

    finalizarVenda(carrinho, vendedorPedro, hoje)
}