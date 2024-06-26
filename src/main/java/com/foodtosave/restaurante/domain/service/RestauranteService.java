package com.foodtosave.restaurante.domain.service;

import com.foodtosave.restaurante.domain.exceptions.CNPJIncorretoException;
import com.foodtosave.restaurante.domain.exceptions.IdNaoEncontradoException;
import com.foodtosave.restaurante.domain.model.Restaurante;
import com.foodtosave.restaurante.infra.RestauranteRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> buscaTodos() {
        List<Restaurante>restauranteList = restauranteRepository.buscaTodos();
        if (restauranteList.isEmpty()){
            throw new ServiceException("Lista vazia");
        }
        return restauranteList;
    }
    public Restaurante buscaPorId(Long id) {
        Restaurante restaurante =  restauranteRepository.buscaPorId(id);
        if(Objects.isNull(restaurante)){
            throw new IdNaoEncontradoException("Id = " + id + " nao existe na base.");
        }
        return restaurante;
    }

    public void deletar(Long id) {
        Restaurante restaurante =  restauranteRepository.buscaPorId(id);
        if(Objects.isNull(restaurante)) {
            throw new IdNaoEncontradoException("Id = " + id + " nao existe na base.");
        }
        restauranteRepository.deletar(id);
    }
    public void salvar(Restaurante restaurante) {
        try {
            validarCNPJ(restaurante.getCnpj());
            restauranteRepository.salvar(restaurante);
        } catch (Exception e) {
            throw new ServiceException("Erro ao salvar o restaurante", e);
        }
    }
    public void atualizar(Restaurante restaurante, Long id) {
        try {
            validarCNPJ(restaurante.getCnpj());
            Restaurante restauranteExistente = restauranteRepository.buscaPorId(id);
            if(Objects.isNull(restauranteExistente)) {
               restauranteRepository.salvar(restaurante);
               return;
            }
            restaurante.setId(id);
            restauranteRepository.atualizar(restaurante);
        } catch (Exception e) {
            throw new ServiceException("Erro ao atualizar o restaurante", e);
        }
    }

    private void validarCNPJ(String cnpj) {
        String CNPJSemCaracteresEspeciais = cnpj.replaceAll("[^\\d]", "");
        if (CNPJSemCaracteresEspeciais.length() != 14) {
            throw new CNPJIncorretoException("CNPJ nao contem 14 digitos");
        }
    }
}
