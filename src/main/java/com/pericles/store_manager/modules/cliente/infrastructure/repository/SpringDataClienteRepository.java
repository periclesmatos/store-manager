package com.pericles.store_manager.modules.cliente.infrastructure.repository;

import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByIdAndAtivoTrue(Long id);
    boolean existsByCpfAndAtivoTrue(String cpf);
    boolean existsByEmailAndAtivoTrue(String email);
    Page<Cliente> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            SELECT c FROM Cliente c
            WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))
                OR c.cpf LIKE CONCAT('%', :termo, '%')
            """)
    Page<Cliente> buscarClientesPorTermo(@Param("termo") String termo, Pageable pageable);
}
