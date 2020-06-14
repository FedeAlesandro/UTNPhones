package edu.utn.utnPhones.session;

import edu.utn.utnPhones.models.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

@Component
public class SessionManager {

    Map<String, Session> sessionMap = new Hashtable<>();

    int sessionExpiration = 900000;

    public String createSession(User user) {

        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));

        return token;
    }

    public Session getSession(String token) {

        Session session = sessionMap.get(token);
        if (session!=null) {
            session.setLastAction(new Date(System.currentTimeMillis()));
        }

        return session;
    }

    public void removeSession(String token) {

        sessionMap.remove(token);
    }

    public void expireSessions() {

        for (String k : sessionMap.keySet()) {
            Session v = sessionMap.get(k);

            if (v.getLastAction().getTime() < System.currentTimeMillis() + (sessionExpiration*1000)) {
                System.out.println("Expiring session " + k);
                sessionMap.remove(k);
            }
        }
    }

    public User getCurrentUser(String token) {

        return getSession(token).getLoggedUser();
    }
}
