package pl.sternik.jp.weekend.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.entities.Status;
import pl.sternik.jp.weekend.repositories.PrzypinkaAlreadyExistsException;
import pl.sternik.jp.weekend.repositories.PrzypinkaRepository;
import pl.sternik.jp.weekend.repositories.NoSuchPrzypinkaException;


@Service
@Primary
public class KlaserServiceJ8Impl implements KlaserService {

    @Autowired
    @Qualifier("lista")
    private PrzypinkaRepository przypinki;

    @Override
    public List<Przypinka> findAll() {
        return przypinki.findAll();
    }

    @Override
    public List<Przypinka> findLatest3() {
        return przypinki.findAll().stream().sorted((a, b) -> b.getDataNabycia().compareTo(a.getDataNabycia())).limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Przypinka> findById(Long id) {
        try {
            return Optional.of(przypinki.readById(id));
        } catch (NoSuchPrzypinkaException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Przypinka> create(Przypinka przypinka) {
        try {
            return Optional.of(przypinki.create(przypinka));
        } catch (PrzypinkaAlreadyExistsException e) {
            try {
                return Optional.of(przypinki.readById(przypinka.getNumerKatalogowy()));
            } catch (NoSuchPrzypinkaException e1) {
                return Optional.empty();
            }
        }

    }

    @Override
    public Optional<Przypinka> edit(Przypinka przypinka) {
        try {
            return Optional.of(przypinki.update(przypinka));
        } catch (NoSuchPrzypinkaException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> deleteById(Long id) {
        try {
            przypinki.deleteById(id);
            return Optional.of(Boolean.TRUE);
        } catch (NoSuchPrzypinkaException e) {
            return Optional.of(Boolean.FALSE);
        }
    }

    @Override
    public List<Przypinka> findAllToSell() {
        return przypinki.findAll().stream().filter(p -> Objects.equals(p.getStatus(), Status.DO_SPRZEDANIA))
                .collect(Collectors.toList());
    }
}
