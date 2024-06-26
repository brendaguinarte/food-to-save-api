package com.foodtosave.restaurante.api;

import com.foodtosave.restaurante.domain.exceptions.CNPJIncorretoException;
import com.foodtosave.restaurante.domain.exceptions.IdNaoEncontradoException;
import com.foodtosave.restaurante.domain.model.Restaurante;
import com.foodtosave.restaurante.domain.service.RestauranteService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/api/v1/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    private ResponseEntity<?> buscaTodos(){
        try {
            return ResponseEntity.ok(restauranteService.buscaTodos());
        } catch (ServiceException serviceException) {
            log.info("Info: " + serviceException.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception exception ){
            log.error("Erro : " + exception.getMessage());
            return ResponseEntity.badRequest().body(criaMensagemErro(exception.getMessage()));
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> buscaPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(restauranteService.buscaPorId(id));
        } catch (IdNaoEncontradoException idNaoEncontradoException) {
            log.error("Erro : " + idNaoEncontradoException.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception exception ){
            log.error("Erro : " + exception.getMessage());
            return ResponseEntity.badRequest().body(criaMensagemErro(exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            restauranteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch ( IdNaoEncontradoException idNaoEncontradoException) {
            log.error("Erro : " + idNaoEncontradoException.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception exception ){
            log.error("Erro : " + exception.getMessage());
            return ResponseEntity.badRequest().body(criaMensagemErro(exception.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Restaurante restaurante) {
        try {
            restauranteService.salvar(restaurante);
            return ResponseEntity.created(null).build();
        } catch (CNPJIncorretoException cnpjIncorretoException){
            log.error("Erro: " + cnpjIncorretoException.getMessage());
            return ResponseEntity.badRequest().body(criaMensagemErro(cnpjIncorretoException.getMessage()));
        } catch (Exception exception) {
            log.error("Erro " + exception.getMessage());
            return ResponseEntity.badRequest().body(criaMensagemErro(exception.getMessage()));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
       try {
           restauranteService.atualizar(restaurante, id);
            return ResponseEntity.ok().build();
        } catch (CNPJIncorretoException cnpjIncorretoException) {
           log.error("Erro: " + cnpjIncorretoException.getMessage());
           return ResponseEntity.badRequest().body(criaMensagemErro(cnpjIncorretoException.getMessage()));
       } catch (IdNaoEncontradoException idNaoEncontradoException) {
           return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            log.error("Erro " + exception.getMessage());
            return ResponseEntity.badRequest().body(criaMensagemErro(exception.getMessage()));
        }
    }

    public Map<String, String> criaMensagemErro(String mensagem){
        Map<String, String> detalhesErro = new HashMap<>();
        detalhesErro.put("erro", HttpStatus.BAD_REQUEST.getReasonPhrase());
        detalhesErro.put("mensagem", mensagem);
        return detalhesErro;
    }
}
