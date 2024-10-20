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
        value = "Create a Salesman",
        key = {"create-salesman", "cs"},
        group = "Salesman"
    )
    public void createSalesman(int sid, String firstname, String lastname){
        SalesMan salesman = new SalesMan(sid, firstname, lastname);
        PersonalManager.getInstance().createSalesMan(salesman);
    }

    @ShellMethod(
        value = "Read a Salesman",
        key = {"read-salesman", "rs"},
        group = "Salesman"
    )
    public String readSalesman(int sid){
        return PersonalManager.getInstance().readSalesMan(sid).toString();
    }

    @ShellMethod(
        value = "Read all Salesmen",
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
        value = "Delete a Salesman",
        key = {"delete-salesman", "ds"},
        group = "Salesman"
    )
    public void deleteSalesman(int sid){
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        PersonalManager.getInstance().deleteSalesMan(salesman);
    }

    @ShellMethod(
        value = "Add a goal to a Salesman",
        key = {"add-goal", "ag"},
        group = "Goal"
    )
    public void addGoal(int sid, int gid, String description, int target, int actual, int year) {
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        SocialPerformanceRecord record = new SocialPerformanceRecord(gid, description, target,
            actual, year);
        PersonalManager.getInstance().addSocialPerformanceRecord(salesman, record);
    }

    @ShellMethod(
        value = "Delete a goal from a Salesman",
        key = {"delete-goal", "dg"},
        group = "Goal"
    )
    public void removeGoal(int sid, int gid){
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        SocialPerformanceRecord record = PersonalManager.getInstance().readSocialPerformanceRecord(gid);
        PersonalManager.getInstance().deleteSocialPerformanceRecord(record, salesman);
    }

    @ShellMethod(
        value = "Read all goals from a Salesman",
        key = {"read-goals", "rg"},
        group = "Goal"
    )
    public void readGoals(int sid){
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        List<SocialPerformanceRecord> records = PersonalManager.getInstance().readSocialPerformanceRecord(salesman);
        if(records.isEmpty()){
            System.out.println("No existing goals.");
            return;
        }
        records.forEach(System.out::println);
    }
}
