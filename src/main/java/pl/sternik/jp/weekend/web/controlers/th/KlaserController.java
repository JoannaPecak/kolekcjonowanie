package pl.sternik.jp.weekend.web.controlers.th;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.sternik.jp.weekend.entities.Przypinka;
import pl.sternik.jp.weekend.services.KlaserService;
import pl.sternik.jp.weekend.services.NotificationService;


@Controller
public class KlaserController {

    @Autowired
//    @Qualifier("spring-data")
    @Qualifier("tablica")
//    @Qualifier("lista")
    private KlaserService klaserService;

    @Autowired
    private NotificationService notificationService;

//    @ModelAttribute("statusyAll")
//    public List<Status> populateStatusy() {
//        return Arrays.asList(Status.ALL);
//    }

    @ModelAttribute("PrzypinkiAll")
    public List<Przypinka> populateCoins() {
        return this.klaserService.findAll();
    }


    @RequestMapping({ "/", "/index" })
    public String index(Model model) {
        return "th/index";
    }

    @RequestMapping(value = "/przypinki", method = RequestMethod.GET)
    public String showMainPage(Model model) {
        model.addAttribute("MyMessages",  notificationService.getNotificationMessages());
        return "th/klaser";
    }

    @RequestMapping("/tosell")
    public String showToSellPage() {
        return "th/tosell";
    }

}
