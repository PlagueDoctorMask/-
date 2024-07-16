package ru.gb.Client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.Client.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
