package vezbe.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vezbe.demo.dto.RegisterDto;
import vezbe.demo.entity.Korisnik;
import vezbe.demo.entity.Kupac;
import vezbe.demo.entity.Uloga;
import vezbe.demo.service.RegisterService;
import vezbe.demo.service.SessionService;

import java.util.HashMap;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private SessionService sessionService;

    @PostMapping("api/register")
    public ResponseEntity Register(@RequestBody RegisterDto registerDto){
        HashMap<String, String> errorDic = Validate(registerDto);

        if (!errorDic.isEmpty()){
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);
        }

        Kupac kupac = registerDto.ToKupac();

        try{
            registerService.Register(kupac, Uloga.KUPAC);
        } catch (Exception e){
            errorDic.put("Username", e.getMessage());
        }

        if (!errorDic.isEmpty())
            return new ResponseEntity(errorDic, HttpStatus.BAD_REQUEST);

        return new ResponseEntity("Ok", HttpStatus.OK);
    }

    private HashMap<String, String> Validate(RegisterDto registerDto){
        HashMap<String, String> errorDic = new HashMap<>();

        if (registerDto.getName() == null)
            errorDic.put("name", "Name is mandatory");
        else if(registerDto.getName().isEmpty())
            errorDic.put("name", "Name is mandatory");

        if (registerDto.getSurname() == null)
            errorDic.put("surname", "Surname is mandatory");
        else if(registerDto.getSurname().isEmpty())
            errorDic.put("surname", "Surname is mandatory");

        if (registerDto.getUsername() == null)
            errorDic.put("username", "Username is mandatory");
        else if(registerDto.getUsername().isEmpty())
            errorDic.put("username", "Username is mandatory");

        if (registerDto.getPassword() == null)
            errorDic.put("password", "Password is mandatory");
        else if(registerDto.getPassword().isEmpty())
            errorDic.put("password", "Password is mandatory");

        return errorDic;
    }








}