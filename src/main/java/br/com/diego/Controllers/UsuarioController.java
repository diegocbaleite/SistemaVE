package br.com.diego.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.diego.Models.Usuario;
import br.com.diego.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario/home")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("nomeValue") String nomeValue, @RequestParam("senha") String senha) {
        Usuario usuario = (Usuario) usuarioRepository.findByNome(nomeValue); // Use o método correto findByUsuarioContaining
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return "redirect:/";
        } else {
            return "redirect:/usuario/home?error=true";
        }
    }
    
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // ENCERRE A SESSÃO ATUAL
        session.invalidate();
        //REDIRECIONE PARA A PÁGINA DE LOGIN APÓS FAZER O LOGOUT
        return "redirect:/usuario/home"; //REDIRECIONE PARA A PÁGINA DE LOGIN
    }
}
