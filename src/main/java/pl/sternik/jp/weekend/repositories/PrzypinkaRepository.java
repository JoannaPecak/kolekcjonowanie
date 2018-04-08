package pl.sternik.jp.weekend.repositories;

import java.util.List;

import pl.sternik.jp.weekend.entities.Przypinka;


public interface PrzypinkaRepository {
    Przypinka create(Przypinka przypinka) throws PrzypinkaAlreadyExistsException;
    Przypinka readById(Long id) throws NoSuchPrzypinkaException;
    Przypinka update(Przypinka przypnka) throws NoSuchPrzypinkaException;
    void deleteById(Long id) throws NoSuchPrzypinkaException;
    List<Przypinka> findAll();
}