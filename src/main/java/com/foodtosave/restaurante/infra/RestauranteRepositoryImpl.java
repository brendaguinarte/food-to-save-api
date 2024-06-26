package com.foodtosave.restaurante.infra;

import com.foodtosave.restaurante.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CacheConfig(cacheNames = "restauranteCache")
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Cacheable(cacheNames = "restaurantes", key = "\"todos\"", unless = "#result == null")
    public List<Restaurante> buscaTodos(){
        return jdbcTemplate.query("SELECT * FROM RESTAURANTE",
                (resultSet, rowNumber) -> new Restaurante(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("cnpj"),
                        resultSet.getString("tipo_cozinha")
                )
        );
    }

    @Cacheable(cacheNames = "restaurantePorId", key = "#id", unless = "#result == null")
    @TimeToLive()
    public Restaurante buscaPorId(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM RESTAURANTE WHERE id = ?",
                    (resultSet, rowNumber) -> new Restaurante(
                            resultSet.getLong("id"),
                            resultSet.getString("nome"),
                            resultSet.getString("cnpj"),
                            resultSet.getString("tipo_cozinha")
                    ),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer salvar(Restaurante restaurante) {
        return jdbcTemplate.update(
                "INSERT INTO RESTAURANTE (nome, cnpj, tipo_cozinha) VALUES (?, ?, ?)",
                restaurante.getNome(), restaurante.getCnpj(), restaurante.getTipoCozinha()
        );
    }

      public Integer atualizar(Restaurante restaurante) {
        return jdbcTemplate.update(
                "UPDATE RESTAURANTE SET nome = ?, cnpj = ?, tipo_cozinha = ? WHERE id = ?",
                restaurante.getNome(), restaurante.getCnpj(), restaurante.getTipoCozinha(), restaurante.getId()
        );
    }

    public Integer deletar(Long id) {
        return jdbcTemplate.update("DELETE FROM RESTAURANTE WHERE id = ?", id);
    }
}
