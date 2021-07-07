package br.com.zup.edu

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
    val server = ServerBuilder
        .forPort(50051)
        .addService(FuncionarioEndpoint())//add um endpoint para ficar rodando
        .build()

    server.start()
    server.awaitTermination()
}

class FuncionarioEndpoint : FuncionarioServiceGrpc.FuncionarioServiceImplBase() {
    override fun cadastrar(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {
        //pegando os dados que vem da requsição grpc
        var nome: String? = request?.nome
        if (!request?.hasField(FuncionarioRequest.getDescriptor().findFieldByName("nome"))!!)
            nome = "[???]"
        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()
        //criando a resposta para o client
        val response = FuncionarioResponse.newBuilder()
            .setNome(nome)
            .setCriadoEm(criadoEm)
            .build()

        responseObserver?.onNext(response)//responde para o client rpc
        responseObserver?.onCompleted()
    }
}