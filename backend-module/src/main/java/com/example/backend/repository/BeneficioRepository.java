package com.example.backend.repository;

import com.example.backend.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

    @Query(value = "SELECT * FROM BENEFICIO WHERE ativo = TRUE", nativeQuery = true)
    List<Beneficio> findAllAtivosNative();
}