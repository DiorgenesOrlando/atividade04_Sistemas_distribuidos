package edu.br.ifsc.atividade03iot;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class Relogio {
	public String ip; 
	public long hora;
	public long minuto;
	public long segundo;	
	
	public Relogio(String ip) {
		this.ip = ip;
	}

	public Relogio() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Relogio ["+ hora + ":" + minuto + ":" + segundo + "]";
	}
	
}
