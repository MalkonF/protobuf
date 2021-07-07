package br.com.zup.edu

import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val request = FuncionarioRequest.newBuilder()
        .setNome("Malkon Faria")
        .setCpf("6678765433")
        .setSalario(2500.00)
        .setIdade(34)
        .setAtivo(true)
        .setCargo(Cargo.DEV)//o cargo nao vai aparecer porque DEV é posição 0 e por isso
        //é considerado DEFAULT. Entao quando a info chega no outro lado, ele já sabe qual valor é o default
        //por isso n precisa serializar e enviar. Aqui nao vai aparecer porque estamos lendo um
        //um builder gerado localmente, mas se tivessemos enviado ia aparecer o cargo
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
                .setLogradouro("Rua ")
                .setCep("7400000")
                .setComplemento("Esquina com av muller")
                .build()
        ).build()

    println(request)
    // escrevendo obj em arquivo binário. Serializa
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    // le  arquivo binário
    val request2 = FuncionarioRequest.newBuilder()
        .mergeFrom(FileInputStream("funcionario-request.bin"))
    request2.cargo = Cargo.QA
    println(request2)

}