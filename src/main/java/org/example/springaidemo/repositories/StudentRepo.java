package org.example.springaidemo.repositories;

import org.example.springaidemo.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Students, Long> {
    public Students findByName(String name);
}
