package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.LoginDto;
import vezbe.demo.entity.Korisnik;
import vezbe.demo.service.LoginService;
import vezbe.demo.service.SessionService;

import javax.management.InvalidAttributeValueException;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SessionService sessionService;



    @PostMapping("api/login")
    public ResponseEntity Login(@RequestBody LoginDto loginDto, HttpSession session)
    {
        Hashtable<String, String>errorDic = new Hashtable<>();


        if(loginDto.getUsername() == null || loginDto.getUsername().isEmpty())
            errorDic.put("username", "Username is mandatory");
        if (loginDto.getPassword() == null || loginDto.getPassword().isEmpty())
            errorDic.put("password", "Password is mandatory");

        if (!errorDic.isEmpty())
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        Korisnik loggedKorisnik = null;

        try {
            loggedKorisnik = loginService.Login(loginDto.getUsername(), loginDto.getPassword());
        } catch (AccountNotFoundException e) {
            errorDic.put("Username", e.getMessage());
        } catch (InvalidAttributeValueException e) {
            errorDic.put("Password", e.getMessage());
        }

        if (!errorDic.isEmpty() || loggedKorisnik == null)
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        session.setAttribute("korisnik", loggedKorisnik);
        session.setAttribute("role", loggedKorisnik.getUloga());
        session.setAttribute("username", loggedKorisnik.getUsername());

        return new ResponseEntity(loggedKorisnik, HttpStatus.OK);
    }

    @PostMapping("api/logout")
    public ResponseEntity logout(HttpSession session) {
        if(!sessionService.validateSession(session))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Uspesno ste se odjavili", HttpStatus.OK);
    }
}
