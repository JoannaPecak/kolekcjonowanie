package pl.sternik.jp.weekend.web.controlers.th;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.entities.Status;
import pl.sternik.jp.weekend.services.KlaserService;
import pl.sternik.jp.weekend.services.NotificationService;
import pl.sternik.jp.weekend.services.NotificationService.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PrzypinkiController {

    @Autowired
    private Logger logger; 
    
    @Autowired
    // @Qualifier("spring-data")
    @Qualifier("tablica")
    // @Qualifier("lista")
    private KlaserService klaserService;

    @Autowired
    private NotificationService notifyService;

    @ModelAttribute("statusyAll")
    public List<Status> populateStatusy() {
        return Arrays.asList(Status.ALL);
    }
    
    @ModelAttribute("MyMessages")
    public List<NotificationMessage> populateMessages() {
        logger.info("Daj messagesy!");
        return notifyService.getNotificationMessages();
    }
    

    @GetMapping(value = "/przypinki/{id}")
    public String view(@PathVariable("id") Long id, final ModelMap model) {
        Optional<Przypinka> result;
        result = klaserService.findById(id);
        if (result.isPresent()) {
            Przypinka przypinka = result.get();
            model.addAttribute("przypinka", przypinka);
            return "th/przypinka";
        } else {
            notifyService.addErrorMessage("Cannot find  #" + id);
            model.clear();
            return "redirect:/przypinki";
        }
    }

    @RequestMapping(value = "/przypinki/{id}/json", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Przypinka> viewAsJson(@PathVariable("id") Long id, final ModelMap model) {
        Optional<Przypinka> result;
        result = klaserService.findById(id);
        if (result.isPresent()) {
            Przypinka przypinka = result.get();
            return new ResponseEntity<Przypinka>(przypinka, HttpStatus.OK);
        } else {
            notifyService.addErrorMessage("Cannot find #" + id);
            model.clear();
            return new ResponseEntity<Przypinka>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "przypinki", params = { "save" }, method = RequestMethod.POST)
    public String saveMoneta(Przypinka przypinka, BindingResult bindingResult, ModelMap model) {
        // @Valid
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            model.addAttribute("MyMessages",  notifyService.getNotificationMessages());
            return "th/przypinka";
        }
        
        if (przypinka.getStatus() == Status.NOWA) {
            przypinka.setStatus(Status.STARA);
        }
        
        Optional<Przypinka> result = klaserService.edit(przypinka);
        if (result.isPresent())
            notifyService.addInfoMessage("Zapis udany");
        else
            notifyService.addErrorMessage("Zapis NIE udany");
        model.clear();
        return "redirect:/przypinki";
    }

    @RequestMapping(value = "/przypinki", params = { "create" }, method = RequestMethod.POST)
    public String createMoneta(Przypinka przypinka, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            model.addAttribute("MyMessages",  notifyService.getNotificationMessages());
            return "th/przypinka";
        }
        klaserService.create(przypinka);
        model.clear();
        notifyService.addInfoMessage("Zapis nowej udany");
        return "redirect:/przypinki";
    }

    @RequestMapping(value = "/przypinki", params = { "remove" }, method = RequestMethod.POST)
    public String removeRow(final Przypinka przypinka, final BindingResult bindingResult, final HttpServletRequest req, @RequestParam Integer remove) {
//        final Integer rowId = Integer.valueOf(req.getParameter("remove"));
        Optional<Boolean> result = klaserService.deleteById(remove.longValue());
        return "redirect:/przypinki";
    }

    @RequestMapping(value = "/przypinki/create", method = RequestMethod.GET)
    public String showMainPages(final Przypinka przypinka) {
        
        przypinka.setDataNabycia(Calendar.getInstance().getTime());
        return "th/przypinka";
    }
}