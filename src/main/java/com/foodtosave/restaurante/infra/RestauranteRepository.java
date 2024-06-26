package com.foodtosave.restaurante.infra;

import com.foodtosave.restaurante.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository {
     Integer salvar(Restaurante restaurante);
     Restaurante buscaPorId(Long id);
     List<Restaurante> buscaTodos();
     Integer atualizar(Restaurante restaurante);
     Integer deletar(Long id);

}
