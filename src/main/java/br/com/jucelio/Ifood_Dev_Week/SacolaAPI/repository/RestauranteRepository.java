package br.com.jucelio.Ifood_Dev_Week.SacolaAPI.repository;

import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
