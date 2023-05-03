package ru.practicum.admin_access.compilation_event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.admin_access.compilation_event.model.CompilationEvent;

import java.util.List;

@Repository
public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
    @Query("SELECT ce FROM CompilationEvent ce WHERE ce.compilation.id = :compilationId")
    List<CompilationEvent> getByCompilation(Long compilationId);

    @Query("SELECT ce FROM CompilationEvent ce WHERE ce.compilation.pinned = :pinned ")
    List<CompilationEvent> getAllByCompilationPinned(Boolean pinned, Pageable pageable);
}
