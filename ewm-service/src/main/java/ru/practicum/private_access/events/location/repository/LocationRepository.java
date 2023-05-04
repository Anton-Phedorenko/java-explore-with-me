package ru.practicum.private_access.events.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.private_access.events.location.model.Location;
import ru.practicum.private_access.events.location.model.LocationKey;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, LocationKey> {

    @Query("select l from Location l where l.lat = :lat and l.lon = :lon")
    Optional<Location> getByLatAndLon(Float lat, Float lon);
}
