package br.com.diego.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.Models.Motorista;
import br.com.diego.Models.Veiculo;
import br.com.diego.Repository.MotoristaRepository;
import br.com.diego.Repository.VeiculoRepository;
import jakarta.validation.Valid;

@Controller
public class VeiculoController {

	@Autowired
	private VeiculoRepository vr;

	@Autowired
	private MotoristaRepository mr;

	// CADASTRAR VEÍCULO
	
	@GetMapping("/cadastrarVeiculo")
	public String form() {
		return "veiculo/formVeiculo";
	}

	@PostMapping("/cadastrarVeiculo")
	public String form(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarVeiculo";
		}

		vr.save(veiculo);
		attributes.addFlashAttribute("mensagem", "Veículo cadastrado com sucesso!");
		return "redirect:/cadastrarVeiculo";
	}

	// LISTAR VEÍCULOS

	@GetMapping("/veiculos")
	public ModelAndView listaVeiculos() {
		ModelAndView mv = new ModelAndView("veiculo/listaVeiculo");
		Iterable<Veiculo> veiculos = vr.findAll();
		mv.addObject("veiculos", veiculos);
		return mv;
	}

	// DETALHES DOS VEÍCULOS

	@GetMapping("/{codigo}")
	public ModelAndView detalhesVeiculo(@PathVariable("codigo") long codigo) {
		Veiculo veiculo = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("veiculo/detalhesVeiculo");
		mv.addObject("veiculo", veiculo);

		Iterable<Motorista> motoristas = mr.findByVeiculo(veiculo);
		mv.addObject("motoristas", motoristas);
		return mv;
	}

	// DELETAR VEÍCULOS

	@GetMapping("/deletarVeiculo")
	public String deletarVeiculo(long codigo, RedirectAttributes attributes) {
	    Veiculo veiculo = vr.findByCodigo(codigo);

	    if (veiculo != null) {
	        vr.delete(veiculo);

	        
	        attributes.addFlashAttribute("successMessage", "Veículo deletado com sucesso!");
	    } else {
	        
	        attributes.addFlashAttribute("errorMessage", "Veículo não encontrado");
	    }

	    return "redirect:/veiculos";
	}

	// ADICIONAR VEÍCULOS

	@PostMapping("/{codigo}")
	public String deletarVeiculoPost(@PathVariable("codigo") long codigo, @Valid Motorista motorista,
			BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{codigo}";
		}

		// CNH DUPLICADO

		if (mr.findBycnh(motorista.getCnh()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "CNH duplicado");
			return "redirect:/{codigo}";
		}

		Veiculo veiculo = vr.findByCodigo(codigo);
		motorista.setVeiculo(veiculo);
		mr.save(motorista);
		attributes.addFlashAttribute("mensagem", "Motorista adicionado com sucesso!");
		return "redirect:/{codigo}";
	}

	// DELETAR MOTORISTA PELA CNH
	
	@GetMapping("/deletarMotorista")
	public String deletarMotorista(String cnh) {
		Motorista motorista = mr.findBycnh(cnh);
		Veiculo veiculo = motorista.getVeiculo();
		String codigo = "" + veiculo.getCodigo();
		
		mr.delete(motorista);
		return "redirect:/" + codigo;

	}

	// MÉTODOS QUE ATUALIZAM O VEÍCULO
	// FORMULÁRIO EDIÇÃO DO VEÍCULO

	@GetMapping("/editar-veiculo")
	public ModelAndView editarVeiculo(long codigo) {
		Veiculo veiculo = vr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("veiculo/update-veiculo");
		mv.addObject("veiculo", veiculo);
		return mv;
	}

	// UPDATE VEICULO
	@PostMapping("/editar-veiculo")
	public String updateVeiculo(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attributes) {
	    if (result.hasErrors()) {
	        // Se houver erros de validação, retorne à página de edição
	        return "veiculo/update-veiculo";
	    }

	    vr.save(veiculo);
	    attributes.addFlashAttribute("successMessage", "Veículo editado com sucesso!");

	    // Use o valor do código diretamente, sem a conversão para String
	    return "redirect:/" + veiculo.getCodigo();
	}
	
}	

