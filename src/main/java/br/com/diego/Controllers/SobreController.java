package br.com.diego.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SobreController {
    @GetMapping("/sobre")
    public String sobrePage() {
        return "sobre";
    }
}

