package de.hbrs.ia.commands;

import de.hbrs.ia.code.PersonalManager;
import de.hbrs.ia.db.MongoConnection;
import de.hbrs.ia.model.SalesMan;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SalesmanCommand {

    @ShellMethod("creates a salesman")
    public void create(String firstname, String lastname, Integer sid) {
        SalesMan record = new SalesMan(firstname, lastname, sid);
        try {
            PersonalManager.getInstance().createSalesMan(record);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
