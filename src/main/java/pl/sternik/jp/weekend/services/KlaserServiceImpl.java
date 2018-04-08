package pl.sternik.jp.weekend.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.repositories.PrzypinkaAlreadyExistsException;
import pl.sternik.jp.weekend.repositories.PrzypinkaRepository;
import pl.sternik.jp.weekend.repositories.NoSuchPrzypinkaException;


@Service
@Qualifier("tablica")
public class KlaserServiceImpl implements KlaserService {

    @Autowired
    @Qualifier("tablica")
    private PrzypinkaRepository bazaDanych;

    @Override
    public List<Przypinka> findAll() {
        return bazaDanych.findAll();
    }

    @Override
    public List<Przypinka> findAllToSell() {
        return bazaDanych.findAll();
    }

    @Override
    public Optional<Przypinka> findById(Long id) {
        try {
            return Optional.of(bazaDanych.readById(id));
        } catch (NoSuchPrzypinkaException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Przypinka> create(Przypinka przypinka) {
        try {
            return Optional.of(bazaDanych.create(przypinka));
        } catch (PrzypinkaAlreadyExistsException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Przypinka> edit(Przypinka przypinka) {
        try {
            return Optional.of(bazaDanych.update(przypinka));
        } catch (NoSuchPrzypinkaException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Boolean> deleteById(Long id) {
        try {
            bazaDanych.deleteById(id);
            return Optional.of(Boolean.TRUE);
        } catch (NoSuchPrzypinkaException e) {
            return Optional.of(Boolean.FALSE);
        }
    }

    @Override
    public List<Przypinka> findLatest3() {
        //TODO: 
        return Collections.emptyList();
    }

}
