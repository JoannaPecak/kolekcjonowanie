package pl.sternik.jp.weekend.services;

import java.util.List;
import java.util.Optional;

import pl.sternik.jp.weekend.entities.Przypinka;


public interface KlaserService {
    List<Przypinka> findAll();

    List<Przypinka> findAllToSell();

    Optional<Przypinka> findById(Long id);

    Optional<Przypinka> create(Przypinka przypinka);

    Optional<Przypinka> edit(Przypinka przypinka);

    Optional<Boolean> deleteById(Long id);

    List<Przypinka> findLatest3();
}