package br.com.jucelio.Ifood_Dev_Week.SacolaAPI.repository;

import br.com.jucelio.Ifood_Dev_Week.SacolaAPI.model.Sacola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long> {
}
