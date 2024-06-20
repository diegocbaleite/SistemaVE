package br.com.diego.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.diego.Models.Dependentes;
import br.com.diego.Models.Funcionario;
import br.com.diego.Repository.DependentesRepository;
import br.com.diego.Repository.FuncionarioRepository;
import jakarta.validation.Valid;


@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private DependentesRepository dr;

	// CHAMO O FORM DE CADASTRAR ROTAS
	@GetMapping("/cadastrarRota")
	public String form() {
		return "funcionario/formFuncionario";
	}

	// CADASTRAR ROTAS
	@PostMapping("/cadastrarRota")
	public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarRota";
		}

		fr.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Rota cadastrada com sucesso!");
		return "redirect:/cadastrarRota";
	}

	// LISTAR ROTAS
	@RequestMapping("/rotas")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}

	// LISTAR ESCOLAS
	@GetMapping("/escolas/{id}")
	public ModelAndView dependentes(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/dependentes");
		mv.addObject("funcionarios", funcionario);

		// LISTA DE ESCOLAS BASEADA NA ROTA
		Iterable<Dependentes> dependentes = dr.findByFuncionario(funcionario);
		mv.addObject("escolas", dependentes);

		return mv;

	}

	// ADICIONAR ESCOLAS
	@PostMapping("/escolas/{id}")
	public String dependentesPost(@PathVariable("id") long id, Dependentes dependentes, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/escolas/{id}";
		}

		if (dr.findByCpf(dependentes.getCpf()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "Código duplicado");
			return "redirect:/escolas/{id}";
		}

		Funcionario funcionario = fr.findById(id);
		dependentes.setFuncionario(funcionario);
		dr.save(dependentes);
		attributes.addFlashAttribute("mensagem", "Escola adicionado com sucesso");
		return "redirect:/escolas/{id}";

	}
	
	// DELETAR ROTAS 
	
	@RequestMapping("/deletarFuncionario")
	public String deletarFuncionario(@RequestParam long id, RedirectAttributes attributes) {
	    Funcionario funcionario = fr.findById(id);

	    if (funcionario != null) {
	        fr.delete(funcionario);

	        // Adicione uma mensagem de sucesso aos atributos de redirecionamento
	        attributes.addFlashAttribute("successMessage", "Rota deletada com sucesso!");

	    }

	    return "redirect:/funcionarios"; 
	    //?successMessage= Rota deletada com sucesso!";

	}


	// MÉTODOS QUE ATUALIZAM AS ROTAS
	// form
	@GetMapping("/editar-rota")
	public ModelAndView editarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	// UPDATE ROTAS
	@PostMapping("/editar-rota")
	public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result,
	        RedirectAttributes attributes) {

	    if (result.hasErrors()) {
	       
	    } else {
	        fr.save(funcionario);
	        attributes.addFlashAttribute("successMessage", "Rota editada com sucesso!");
	    }

	    long idLong = funcionario.getId();
	    String id = "" + idLong;
	    return "redirect:/dependentes/" + id;
	}


	// DELETAR ESCOLAS
	@RequestMapping("/deletarDependente")
	public String deletarDependente(String cpf) {
		Dependentes dependente = dr.findByCpf(cpf);

		Funcionario funcionario = dependente.getFuncionario();
		String codigo = "" + funcionario.getId();

		dr.delete(dependente);
		return "redirect:/escolas/" + codigo;

	}

}
