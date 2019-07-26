package net.proselyte.springapp.controller;


import net.proselyte.springapp.model.User;
import net.proselyte.springapp.service.SecurityService;
import net.proselyte.springapp.service.UserService;
import net.proselyte.springapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(types = User.class)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    private static final String ACCOUNT_SID = "AC57e4ca3ba8013a7c8d2dc87b485ee1f0";
    private static final String AUTH_TOKEN = "4f2ae8e7850f33d892feb6d02c50425a";
    private static final String TWILIO_NUMBER = "+15312141432";
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, @RequestParam("phoneCodeFromUser") String phoneCodeFromUserInput, Model model, BindingResult bindingResult, SessionStatus sessionStatus) {
        userValidator.validate(userForm, bindingResult);

        if (!phoneCodeFromUserInput.equals(userForm.getPhoneCode())) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        sessionStatus.setComplete();
        return "redirect:/login";
    }

    @GetMapping({"/login"})
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

    @PostMapping("/confirmPhoneNumber")
    public String showFormForUpdate(User user, Model model) {

        User currentUser = userService.findByUsername(user.getUsername());
        if (currentUser != null) {
            model.addAttribute("message", "This user exists!");
            return "registration";
        } else {
            String randomPhoneCode = String.valueOf((int) (Math.random() * 9000)+1000);
            user.setPhoneCode(randomPhoneCode);



            Message.creator(
                    new PhoneNumber(user.getPhoneNumber()),
                    new PhoneNumber(TWILIO_NUMBER),
                    "Your verification code is " + randomPhoneCode)
                    .create();

            return "confirm_phone";
        }
    }
}
