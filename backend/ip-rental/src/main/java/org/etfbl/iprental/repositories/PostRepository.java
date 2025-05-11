package org.etfbl.iprental.repositories;


import org.etfbl.iprental.models.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}
