package edu.br.ifsc.atividade03iot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class Atividade04Server {



	public static void main(String[] args) throws InterruptedException {
		Relogio relogioServer = new Relogio("localhost:8080");
		// Create two threads that will increment the counter
		Thread thread1 = new Thread(new Contador(relogioServer));
		//Thread thread2 = new Thread(new IncrementTask(counter));

		SpringApplication.run(Atividade04Server.class, args);
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		List<Relogio> lista = new ArrayList<>();
		Relogio relogio1 = new Relogio("http://localhost:8081");
		//	lista.add(relogio1);
		Relogio relogio2 = new Relogio("http://ec2-54-94-178-111.sa-east-1.compute.amazonaws.com:8082");
		lista.add(relogio2);
		Relogio relogio3 = new Relogio("localhost:8083");
		//lista.add(relogio3);
		Relogio relogio4 = new Relogio("localhost:8084");
		//lista.add(relogio4);

		thread1.start();
		//thread1.join();

		scheduler.scheduleAtFixedRate(() -> {
			System.out.println(relogioServer.toString());


			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			long totalSegundos = 0;
			totalSegundos += (relogioServer.hora*3600) + (relogioServer.minuto*60) + (relogioServer.segundo);

			
			for(Relogio relogio : lista) {


				String apiUrl = relogio.ip + "/horario-atual";
				String requestBody = ""
						+ "{    \"hora\":"+relogioServer.hora+",\n"
						+ "    \"minuto\":" +relogioServer.minuto+",\n"
						+ "    \"segundo\":"+relogioServer.segundo+"\n}";
				// Crie uma solicitação HTTP com o método POST
				//  System.out.println(requestBody);
				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

				try {
					ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

					// Verifique a resposta
					if (responseEntity.getStatusCode() == HttpStatus.OK) {
						String responseBody = responseEntity.getBody();
						//  System.out.println("Resposta da API: " + responseBody);         

						// Deserialize the JSON response into a Relogio object
						ObjectMapper objectMapper = new ObjectMapper();
						Relogio resultado = objectMapper.readValue(responseBody, Relogio.class);
						resultado.ip = relogio.ip;
						totalSegundos += (resultado.hora*3600) + (resultado.minuto*60) + (resultado.segundo);

						// atualiza o valor do objeto elogio na lista
						int index = lista.indexOf(relogio);
						lista.set(index, resultado);

					} else {
						System.err.println("Erro na solicitação. Código de status: " + responseEntity.getStatusCodeValue());
					}
				} catch (Exception e) {
					System.err.println("Erro ao enviar a solicitação: " + e.getMessage());
				}
			}

			// calcular o tempo medio para todos os relogios

			Relogio relogioAtualizado = new Relogio();
			totalSegundos = totalSegundos/5;
			long hours = totalSegundos / 3600;
			long minutes = (totalSegundos % 3600) / 60;
			long seconds = totalSegundos % 60;
			relogioAtualizado.hora = hours;
			relogioAtualizado.minuto = minutes;
			relogioAtualizado.segundo = seconds;
			relogioServer.hora = hours;
			relogioServer.minuto = minutes;
			relogioServer.segundo = seconds;
			for (Relogio r : lista) {
				relogioAtualizado.ip = r.ip;
				
				String apiUrl = relogioAtualizado.ip + "/atualizar";
				String requestBody = ""
						+ "{    \"hora\":"+relogioAtualizado.hora+",\n"
						+ "    \"minuto\":" +relogioAtualizado.minuto+",\n"
						+ "    \"segundo\":"+relogioAtualizado.segundo+"\n}";
				// Crie uma solicitação HTTP com o método POST
				//  System.out.println(requestBody);
				HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

				try {
					ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

					// Verifique a resposta
					if (responseEntity.getStatusCode() == HttpStatus.OK) {
						String responseBody = responseEntity.getBody();
						//  System.out.println("Resposta da API: " + responseBody);         

						// Deserialize the JSON response into a Relogio object
						ObjectMapper objectMapper = new ObjectMapper();
						Relogio resultado = objectMapper.readValue(responseBody, Relogio.class);				

					} else {
						System.err.println("Erro na solicitação. Código de status: " + responseEntity.getStatusCodeValue());
					}
				} catch (Exception e) {
					System.err.println("Erro ao enviar a solicitação: " + e.getMessage());
				}

			}

		}, 0, 30, TimeUnit.SECONDS);

		// Mantenha o programa em execução indefinidamente
		while (true) {
			try {
				Thread.sleep(1000); // Durma por 1 segundo para manter o programa em execução
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}



