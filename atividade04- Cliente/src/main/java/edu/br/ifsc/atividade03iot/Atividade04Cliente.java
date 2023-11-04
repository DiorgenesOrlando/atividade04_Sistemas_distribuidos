package edu.br.ifsc.atividade03iot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


@SpringBootApplication
public class Atividade04Cliente {

	public static Relogio relogioServer = new Relogio();


	public static void main(String[] args) throws InterruptedException {
		// Create two threads that will increment the counter
        Thread thread1 = new Thread(new Contador(relogioServer));
        Thread thread2 = new Thread(new GeradorDeAtrasos(relogioServer));

        //Thread thread2 = new Thread(new IncrementTask(counter));
		
		SpringApplication.run(Atividade04Cliente.class, args);
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		Relogio relogio1 = new Relogio("localhost:8081");
		Relogio relogio2 = new Relogio("localhost:8082");
		Relogio relogio3 = new Relogio("localhost:8083");
		Relogio relogio4 = new Relogio("localhost:8084");
		
		thread1.start();
		thread2.start();
		//thread1.join();
		
		scheduler.scheduleAtFixedRate(() -> {
			
			
		}, 0, 1, TimeUnit.SECONDS);

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



