package edu.br.ifsc.atividade03iot;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GeradorDeAtrasos implements Runnable {
	Relogio relogioServer;
	
	public GeradorDeAtrasos(long hora, long minut, long segundo) {
		this.relogioServer = relogioServer;
	}

	public GeradorDeAtrasos(Relogio relogioServer) {
		this.relogioServer = relogioServer;
		// TODO Auto-generated constructor stub
	}

	public Relogio horarioAtual() {
		return relogioServer;
	}
	
	public Relogio contagem() {

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {			
			 Random random = new Random();
			 relogioServer.hora = random.nextInt( 23);
			 relogioServer.minuto = random.nextInt(59);
			 relogioServer.segundo = random.nextInt(59);
			
			System.out.println(relogioServer.toString());			
		}, 0, 20, TimeUnit.SECONDS);

		// Mantenha o programa em execução indefinidamente
		while (true) {
			try {
				Thread.sleep(1000); // Durma por 1 segundo para manter o programa em execução
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		contagem();
		
	}



}
