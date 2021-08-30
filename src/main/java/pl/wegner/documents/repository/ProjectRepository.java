package pl.wegner.documents.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wegner.documents.model.entities.Ink;
import pl.wegner.documents.model.entities.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query("SELECT i FROM Project p JOIN p.inks i")
    List<Ink> findAllInks();

//    List<Project> findAllBy(Pageable pageable, Specification<Project> specification);
}
