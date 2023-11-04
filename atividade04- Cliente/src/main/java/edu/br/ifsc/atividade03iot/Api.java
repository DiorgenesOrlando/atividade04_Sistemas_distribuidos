package edu.br.ifsc.atividade03iot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api  {
	
	@PostMapping("/horario-atual")
	public Relogio RetornarHorario (@RequestBody Relogio relogio) {
		
		Relogio relogioRetorno = new Relogio();
		//relogioRetorno.segundo = (Atividade04Cliente.relogioServer.segundo - relogio.segundo);
	//	relogioRetorno.minuto = (Atividade04Cliente.relogioServer.minuto - relogio.minuto);
		//relogioRetorno.hora = (Atividade04Cliente.relogioServer.hora - relogio.hora);
		//System.out.println("Relogio Retorno" + relogio.toString());
		
		return Atividade04Cliente.relogioServer;
	}
	
	@PostMapping("/atualizar")
	public Relogio AtualizarHorario (@RequestBody Relogio relogio) {
		Atividade04Cliente.relogioServer.hora = relogio.hora;
		Atividade04Cliente.relogioServer.minuto = relogio.minuto;
		Atividade04Cliente.relogioServer.segundo = relogio.segundo;

		return Atividade04Cliente.relogioServer;
	}
	
}
