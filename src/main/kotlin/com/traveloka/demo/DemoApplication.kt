package com.traveloka.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@RestController
class DemoController(webClientBuilder: WebClient.Builder) {

	private val webClient = webClientBuilder.build()

	@GetMapping("/")
	fun demo(@RequestParam delay: Long): Mono<String> {
		return webClient.get()
			.uri("http://localhost:8080/delay?delay=$delay")
			.retrieve()
			.bodyToMono(String::class.java)
	}
}