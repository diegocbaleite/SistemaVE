package br.com.diego.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.diego.Repository.DependentesRepository;
import br.com.diego.Repository.FuncionarioRepository;
import br.com.diego.Repository.MotoristaRepository;
import br.com.diego.Repository.VeiculoRepository;

@Controller
public class BuscaController {
	
	@Autowired
	private FuncionarioRepository fr;
	
	@Autowired
	private VeiculoRepository vr;
	
	@Autowired
	private DependentesRepository dr;
	
	@Autowired
	private MotoristaRepository mr;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome) {
		
		ModelAndView mv = new ModelAndView("index");
		String mensagem = "Resultados da busca por " + buscar;
		
		if(nome.equals("nomefuncionario")) {
			mv.addObject("funcionarios", fr.findByNomes(buscar));
			
		}else if(nome.equals("nomedependente")) {
			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
			
		}else if(nome.equals("nomemotorista")) {
			mv.addObject("motorista", mr.findByNomesMotoristas(buscar));
			
		}else if(nome.equals("nomeveiculo")) {
			mv.addObject("veiculos", vr.findByNomesVeiculos(buscar));
			
		}else {
			mv.addObject("funcionarios", fr.findByNomes(buscar));
			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
			mv.addObject("motorista", mr.findByNomesMotoristas(buscar));
			mv.addObject("veiculos", vr.findByNomesVeiculos(buscar));
		}
		
		mv.addObject("mensagem", mensagem);
		
		return mv;
	}

}
