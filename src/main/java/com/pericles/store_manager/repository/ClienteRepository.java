package com.pericles.store_manager.repository;

import com.pericles.store_manager.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            SELECT c FROM Cliente c
            WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR c.cpf LIKE CONCAT('%', :termo, '%')
            """)
    Page<Cliente> buscarClientesPorTermo(@Param("termo") String termo, Pageable pageable);
}
