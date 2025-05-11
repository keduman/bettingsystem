package org.example.bettingsystem.repository;

import org.example.bettingsystem.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
