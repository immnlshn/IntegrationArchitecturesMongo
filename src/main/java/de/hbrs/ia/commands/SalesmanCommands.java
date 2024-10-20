package de.hbrs.ia.commands;

import de.hbrs.ia.code.PersonalManager;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import java.util.List;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SalesmanCommands extends AbstractShellComponent{

    @ShellMethod(
        value = "Erstelle einen Salesman",
        key = {"create-salesman", "cs"},
        group = "Salesman"
    )
    public void createSalesman(int sid, String firstname, String lastname){
        SalesMan salesman = new SalesMan(sid, firstname, lastname);
        PersonalManager.getInstance().createSalesMan(salesman);
    }

    @ShellMethod(
        value = "Lese ein Salesman",
        key = {"read-salesman", "rs"},
        group = "Salesman"
    )
    public String readSalesman(int sid){
        return PersonalManager.getInstance().readSalesMan(sid).toString();
    }

    @ShellMethod(
        value = "Lese alle Salesman",
        key = {"read-all-salesmen", "ras"},
        group = "Salesman"
    )
    public void readAllSalesmen(){
        List<SalesMan> allSalesmen = PersonalManager.getInstance().readAllSalesMen();
        if(allSalesmen.isEmpty()){
            System.out.println("Keine Salesman vorhanden.");
            return;
        }
        allSalesmen.forEach(System.out::println);
    }

    @ShellMethod(
        value = "Lösche einen Salesman",
        key = {"delete-salesman", "ds"},
        group = "Salesman"
    )
    public void deleteSalesman(int sid){
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        PersonalManager.getInstance().deleteSalesMan(salesman);
    }

    @ShellMethod(
        value = "Füge ein Ziel für einen Salesman hinzu",
        key = {"add-goal", "ag"},
        group = "Goal"
    )
    public void addGoal(int sid, int gid, String description, int target, int actual, int year) {
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        SocialPerformanceRecord record = new SocialPerformanceRecord(gid, description, target,
            actual, year);
        PersonalManager.getInstance().addSocialPerformanceRecord(record, salesman);
    }

    @ShellMethod(
        value = "Lösche ein Ziel eines Salesman",
        key = {"delete-goal", "dg"},
        group = "Goal"
    )
    public void removeGoal(int sid, int gid){
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        SocialPerformanceRecord record = PersonalManager.getInstance().readSocialPerformanceRecord(gid);
        PersonalManager.getInstance().deleteSocialPerformanceRecord(record, salesman);
    }

    @ShellMethod(
        value = "Lese alle Ziele eines Salesman",
        key = {"read-goals", "rg"},
        group = "Goal"
    )
    public void readGoals(int sid){
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        List<SocialPerformanceRecord> records = PersonalManager.getInstance().readSocialPerformanceRecord(salesman);
        if(records.isEmpty()){
            System.out.println("Keine Ziele vorhanden.");
            return;
        }
        records.forEach(System.out::println);
    }
}
