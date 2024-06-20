package br.com.diego.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.diego.Models.Funcionario;
import br.com.diego.Models.Motorista;
import br.com.diego.Models.Veiculo;
import br.com.diego.Repository.FuncionarioRepository;
import br.com.diego.Repository.MotoristaRepository;
import br.com.diego.Repository.VeiculoRepository;

@Controller
public class DashboardController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MotoristaRepository motoristaRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Veiculo> veiculos = (List<Veiculo>) veiculoRepository.findAll();
        List<Funcionario> funcionarios = (List<Funcionario>) funcionarioRepository.findAll();
        List<Motorista> motoristas = (List<Motorista>) motoristaRepository.findAll();

        // CALCULE O NÚMERO DE MOTORISTAS
        long motoristasCount = motoristas.size();

        model.addAttribute("veiculos", veiculos);
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("motoristasCount", motoristasCount);

        return "dashboard";
    }
}
