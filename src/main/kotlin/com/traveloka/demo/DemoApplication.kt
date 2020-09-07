package com.traveloka.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@RestController
class DemoController(webClientBuilder: WebClient.Builder) {

	private val webClient = webClientBuilder.build()

	@GetMapping("/")
	fun weclient(@RequestParam delay: Long): Mono<String> {
		return webClient.get()
			.uri("http://localhost:8080/delay?delay=$delay")
			.retrieve()
			.bodyToMono(String::class.java)
	}

	@GetMapping("/reactorNetty")
	fun reactorNetty(@RequestParam delay: Long): Mono<String> {
		return HttpClient.create()
			.get()
			.uri("http://localhost:8080/delay?delay=$delay")
			.responseContent()
			.aggregate()
			.asString()
	}
}