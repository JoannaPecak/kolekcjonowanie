package pl.sternik.jp.weekend.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.entities.Status;
import pl.sternik.jp.weekend.repositories.PrzypinkaRepository;

@Repository
@Qualifier("tablica")
public class ProstaBazaDanych implements PrzypinkaRepository {

    private Przypinka[] baza;

    public ProstaBazaDanych() {
        baza = new Przypinka[15];
        Przypinka m = new Przypinka();
        m.setNumerKatalogowy(0L);
        m.setKrajPochodzenia("Polska");
        m.setWielkosc("mm");
        m.setOpis("Ładna ");
        m.setDataNabycia(new Date());
        m.setCenaNabycia(new BigDecimal("1.2"));
        m.setStatus(Status.NOWA);
        baza[0] = m;
        m = new Przypinka();
        m.setNumerKatalogowy(2L);
        m.setKrajPochodzenia("Polska");
        m.setWielkosc("zł");
        m.setOpis("Ładna nowiutka dwu złotóweczka");
        m.setDataNabycia(new Date());
        m.setCenaNabycia(new BigDecimal("2.2"));
        m.setStatus(Status.DO_SPRZEDANIA);
        baza[2] = m;

    }

    public ProstaBazaDanych(int rozmiarBazy) {
        baza = new Przypinka[rozmiarBazy];
    }

    @Override
    public Przypinka create(Przypinka przypinka) throws PrzypinkaAlreadyExistsException {
        if (przypinka.getNumerKatalogowy() != null && baza[przypinka.getNumerKatalogowy().intValue()] != null) {
            if (przypinka.getNumerKatalogowy().equals(baza[przypinka.getNumerKatalogowy().intValue()].getNumerKatalogowy())) {
                throw new PrzypinkaAlreadyExistsException("");
            }
        }
        for (int i = 0; i < baza.length; i++) {
            if (baza[i] == null) {
                baza[i] = przypinka;
                przypinka.setNumerKatalogowy((long) i);
                return przypinka;
            }
        }
        throw new RuntimeException("");
    }

    @Override
    public void deleteById(Long id) throws NoSuchPrzypinkaException {
        int numerKatalogowy = id.intValue();
        if (!sprawdzPoprawnoscNumeruKatalogowego(numerKatalogowy)) {
            throw new NoSuchPrzypinkaException("Nie poprawny numer katologowy");
        }
        // tu troche zle ;)
        baza[numerKatalogowy] = null;
    }

    @Override
    public Przypinka update(Przypinka przypinka) throws NoSuchPrzypinkaException {
        int numerKatalogowy = przypinka.getNumerKatalogowy().intValue();
        if (!sprawdzPoprawnoscNumeruKatalogowego(numerKatalogowy)) {
            throw new NoSuchPrzypinkaException("Nie poprawny numer katologowy");
        }

        Przypinka m = baza[przypinka.getNumerKatalogowy().intValue()];
        if (m == null) {
            throw new NoSuchPrzypinkaException("Brak.");
        } else {
            baza[przypinka.getNumerKatalogowy().intValue()] = przypinka;
        }
        return przypinka;
    }

    @Override
    public Przypinka readById(Long numerKatalogowy) throws NoSuchPrzypinkaException {
        int id = numerKatalogowy.intValue();
        if (!sprawdzPoprawnoscNumeruKatalogowego(id) || czyWolne(id)) {
            throw new NoSuchPrzypinkaException();
        }
        return baza[id];
    }

    private boolean czyWolne(int id) {
        if(baza[id]!= null)
            return false;
        return true;
    }

    public List<Przypinka> findAll() {
        List<Przypinka> tmp = new ArrayList<>();
        for (int i = 0; i < baza.length; i++) {
            if (baza[i] != null)
                tmp.add(baza[i]);
        }
        return tmp;
    }

    public void wyswietlBaze() {
        for (int i = 0; i < baza.length; i++) {
            System.out.println("" + i + ":" + baza[i]);
        }
    }

    private boolean sprawdzPoprawnoscNumeruKatalogowego(int numerKatalogowy) {
        if (numerKatalogowy < 0 || numerKatalogowy >= baza.length) {
            System.out.println("Zły numer katalogowy");
            return false;
        }
        return true;
    }

}
