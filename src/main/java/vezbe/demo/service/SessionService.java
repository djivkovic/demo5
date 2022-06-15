package vezbe.demo.service;

import org.springframework.stereotype.Service;
import vezbe.demo.entity.Uloga;

import javax.servlet.http.HttpSession;

@Service
public class SessionService
{

    public boolean role(HttpSession session, Uloga role) {

        Object roleSession = session.getAttribute("Role");

        if(roleSession == null)
            return false;
        if(!roleSession.toString().endsWith(role.toString()))
            return false;
        return roleSession.equals(role);

    }

    public String getUsername(HttpSession session) {

        Object username = session.getAttribute("username");
        if(username == null)
            return "";
        if(username.toString().isEmpty())
            return "";
        return username.toString();

    }

    public Uloga getRole(HttpSession session) {
        Object role = session.getAttribute("role");
        if(role == null)
            return null;
        if(role.toString().isEmpty())
            return null;
        return (Uloga) role;
    }

    public boolean validateSession(HttpSession session) {
        if(session == null)
            return false;

        String username = getUsername(session);
        Uloga role = getRole(session);

        if(username == null || username.isEmpty())
            return false;

        if(role == null)
            return false;

        return !role.toString().isEmpty();

    }
}
