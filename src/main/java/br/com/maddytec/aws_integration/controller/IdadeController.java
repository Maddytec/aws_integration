package br.com.maddytec.aws_integration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/idade")
public class IdadeController {

    @GetMapping("/{ano}")
    public ResponseEntity<Integer> getIdade(@PathVariable(name = "ano") Integer anoNascimento){
        Integer anoAtual = LocalDate.now().getYear();
        Integer age = anoAtual - anoNascimento;
        return ResponseEntity.ok().body(age);
    }

}
