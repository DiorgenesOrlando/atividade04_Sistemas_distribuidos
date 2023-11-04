package edu.br.ifsc.atividade03iot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Contador implements Runnable {
	Relogio relogioServer;
	
	public Contador(long hora, long minut, long segundo) {
		this.relogioServer = relogioServer;
	}

	public Contador(Relogio relogioServer) {
		this.relogioServer = relogioServer;
		// TODO Auto-generated constructor stub
	}

	public Relogio horarioAtual() {
		return relogioServer;
	}
	
	public Relogio contagem() {

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
			
			if(relogioServer.segundo<59) {
				relogioServer.segundo ++;
			}else if(relogioServer.segundo == 59 ) {
				relogioServer.segundo = 0;
				if(relogioServer.minuto < 59) {
					relogioServer.minuto ++;
				}else if(relogioServer.minuto == 59) {
					relogioServer.minuto = 0;
					if(relogioServer.hora < 23) {
						relogioServer.hora ++; 
					}else {
						relogioServer.hora = 0;
					}
				}
			}
			System.out.println(relogioServer.toString());			
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		contagem();
		
	}



}
