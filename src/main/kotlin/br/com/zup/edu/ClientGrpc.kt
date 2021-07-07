package br.com.zup.edu

import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val request = FuncionarioRequest.newBuilder()
        .setNome("Malkon Faria")
        .setCpf("5656667677")
        .setSalario(2500.00)
        .setIdade(34)
        .setAtivo(true)
        .setCargo(Cargo.DEV)
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
                .setLogradouro("Rua ")
                .setCep("74000000")
                .setComplemento("Pert Avenida")
                .build()
        ).build()

    val client = FuncionarioServiceGrpc.newBlockingStub(channel)
    val response = client.cadastrar(request)

    println(response)//vai imprimir no console a resposta que o server mandou
}