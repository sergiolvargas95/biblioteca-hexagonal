package co.edu.udec.domain.repositories;

import co.edu.udec.domain.model.aggregates.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyRepository {
    Copy save(Copy copy);
    Optional<Copy> findById(Long id);
    List<Copy> findAll();
    void deleteById(Long id);
}
