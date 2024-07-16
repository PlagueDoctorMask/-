package ru.gb.ServiceAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.ServiceAPI.domain.Profile;

@Repository
public interface ServerRepository extends JpaRepository<Profile, Long>{



}
