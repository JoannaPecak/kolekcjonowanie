package pl.sternik.jp.weekend.web.controlers.th;

import java.util.Date;

import javax.ws.rs.HeaderParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.entities.Status;
import pl.sternik.jp.weekend.repositories.PrzypinkaAlreadyExistsException;
import pl.sternik.jp.weekend.repositories.PrzypinkaRepository;
import pl.sternik.jp.weekend.repositories.NoSuchPrzypinkaException;



@Controller
public class WprawkiControllerTh {

    @Autowired
    @Qualifier("tablica")
    PrzypinkaRepository baza;
    
    @RequestMapping(path = "/wprawki-th", method = RequestMethod.GET)
    public String wprawki(ModelMap model) {
        model.put("msg", "Wielkosc z modelu");
        model.addAttribute("data", new Date());
        return "th/wprawki";
    }

    @GetMapping("/wprawki-th/{cos}")
    public String wprawki(@PathVariable String cos, ModelMap model) {
        model.addAttribute("cos", cos);
        model.put("msg", "Wielkosc z modelu");
        model.addAttribute("data", new Date());
        return "th/wprawki";
    }

    @GetMapping("/wprawki-th2")
    @ResponseBody
    public String wprawkiParam(@RequestParam("cos") String cosParam, ModelMap model) {
        return "Wprawki z param cos=" + cosParam;
    }
    
    @GetMapping("/wprawki-th3")
    @ResponseBody
    public String wprawkiHeader(@RequestHeader("User-Agent") String cosParam, ModelMap model) {
        return "Uzywasz przegladarki:=" + cosParam;
    }
    
    @GetMapping(value = "/wprawki-th/monety/{id}/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Przypinka> viewAsJson(@PathVariable("id") Long id, final ModelMap model) {
        Przypinka m;
        try {
            m = baza.readById(id);
            return new ResponseEntity<Przypinka>(m, HttpStatus.OK);
            
        } catch (NoSuchPrzypinkaException e) {
            System.out.println(e.getClass().getName());
            m = new Przypinka();
            m.setNumerKatalogowy(id);
            m.setKrajPochodzenia("Polska");
            m.setStatus(Status.NOWA);
           
            try {
                baza.create(m);
            } catch (PrzypinkaAlreadyExistsException e1) {
                System.out.println(e1.getClass().getName());
            }
            return new ResponseEntity<Przypinka>(m, HttpStatus.CREATED);
        }
    }

}
