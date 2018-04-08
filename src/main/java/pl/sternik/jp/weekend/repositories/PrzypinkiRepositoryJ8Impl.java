package pl.sternik.jp.weekend.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.entities.Status;


@Service
@Qualifier("lista")
public class PrzypinkiRepositoryJ8Impl implements PrzypinkaRepository {

    private List<Przypinka> przypinki = new ArrayList<Przypinka>() {
        private static final long serialVersionUID = 1L;
        {
            add(Przypinka.producePrzypinka(1L, "Polska", "mm", "nowiutka ", new Date(), new BigDecimal("1.2"),
                    Status.NOWA));
            add(Przypinka.producePrzypinka(2L, "Polska", "mm", "First ", new Date(), new BigDecimal("1.2"),
                    Status.DO_SPRZEDANIA));
            add(Przypinka.producePrzypinka(3L, "Polska", "mm", "Second ", new Date(), new BigDecimal("1.2"), Status.DUBLET));
            add(Przypinka.producePrzypinka(4L, "Polska", "mm", "Forth ", new Date(), new BigDecimal("1.2"),
                    Status.DO_SPRZEDANIA));
            add(Przypinka.producePrzypinka(5L, "Polska", "mm", "Number 5", new Date(), new BigDecimal("1.2"), Status.NOWA));
            add(Przypinka.producePrzypinka(6L, "Polska", "mm", "Sixth ", new Date(), new BigDecimal("1.2"), Status.NOWA));
        }
    };

    @Override
    public List<Przypinka> findAll() {
        return this.przypinki;
    }

    @Override
    public Przypinka readById(Long id) throws NoSuchPrzypinkaException {
        return this.przypinki.stream().filter(p -> Objects.equals(p.getNumerKatalogowy(), id)).findFirst()
                .orElseThrow(NoSuchPrzypinkaException::new);
    }

    @Override
    public Przypinka create(Przypinka przypinka) {
        if (!przypinki.isEmpty()) {
            przypinka.setNumerKatalogowy(
                    this.przypinki.stream().mapToLong(p -> p.getNumerKatalogowy()).max().getAsLong() + 1);
        } else {
            przypinka.setNumerKatalogowy(1L);
        }
        this.przypinki.add(przypinka);
        return przypinka;
    }

    @Override
    public Przypinka update(Przypinka przypinka) throws NoSuchPrzypinkaException {
        for (int i = 0; i < this.przypinki.size(); i++) {
            if (Objects.equals(this.przypinki.get(i).getNumerKatalogowy(), przypinka.getNumerKatalogowy())) {
                this.przypinki.set(i, przypinka);
                return przypinka;
            }
        }
        throw new NoSuchPrzypinkaException("Nie ma : " + przypinka.getNumerKatalogowy());
    }

    @Override
    public void deleteById(Long id) throws NoSuchPrzypinkaException {
        for (int i = 0; i < this.przypinki.size(); i++) {
            if (Objects.equals(this.przypinki.get(i).getNumerKatalogowy(), id)) {
                this.przypinki.remove(i);
            }
        }
        throw new NoSuchPrzypinkaException("Nie ma : " + id);
    }

}
