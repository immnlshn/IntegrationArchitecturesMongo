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
        // create a new Salesman
        SalesMan salesman = new SalesMan(sid, firstname, lastname);
        // add the Salesman to the database
        PersonalManager.getInstance().createSalesMan(salesman);
    }

    @ShellMethod(
        value = "Read a Salesman",
        key = {"read-salesman", "rs"},
        group = "Salesman"
    )
    public String readSalesman(int sid){
        // read a Salesman from the database by its id
        return PersonalManager.getInstance().readSalesMan(sid).toString();
    }

    @ShellMethod(
        value = "Read all Salesmen",
        key = {"read-all-salesmen", "ras"},
        group = "Salesman"
    )
    public void readAllSalesmen(){
        // read all Salesmen from the database
        List<SalesMan> allSalesmen = PersonalManager.getInstance().readAllSalesMen();
        // if there are no Salesmen in the database
        if(allSalesmen.isEmpty()){
            System.out.println("Keine Salesman vorhanden.");
            return;
        }
        // print all Salesmen
        allSalesmen.forEach(System.out::println);
    }

    @ShellMethod(
        value = "Delete a Salesman",
        key = {"delete-salesman", "ds"},
        group = "Salesman"
    )
    public void deleteSalesman(int sid){
        // find the Salesman in the database
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        // delete the Salesman from the database
        PersonalManager.getInstance().deleteSalesMan(salesman);
    }

    @ShellMethod(
        value = "Add a goal to a Salesman",
        key = {"add-goal", "ag"},
        group = "Goal"
    )
    public void addGoal(int sid, int gid, String description, int target, int actual, int year) {
        // find the Salesman in the database
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        // create a new goal
        SocialPerformanceRecord record = new SocialPerformanceRecord(gid, description, target,
            actual, year);
        // add the goal to the Salesman
        PersonalManager.getInstance().addSocialPerformanceRecord(salesman, record);
    }

    @ShellMethod(
        value = "Delete a goal from a Salesman",
        key = {"delete-goal", "dg"},
        group = "Goal"
    )
    public void removeGoal(int sid, int gid){
        // find the Salesman in the database
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        // find the goal in the database
        SocialPerformanceRecord record = PersonalManager.getInstance().readSocialPerformanceRecord(gid);
        // delete the goal from the Salesman
        PersonalManager.getInstance().deleteSocialPerformanceRecord(record, salesman);
    }

    @ShellMethod(
        value = "Read all goals from a Salesman",
        key = {"read-goals", "rg"},
        group = "Goal"
    )
    public void readGoals(int sid){
        // find the Salesman in the database
        SalesMan salesman = PersonalManager.getInstance().readSalesMan(sid);
        // read all goals from the Salesman
        List<SocialPerformanceRecord> records = PersonalManager.getInstance().readSocialPerformanceRecord(salesman);
        // if there are no goals
        if(records.isEmpty()){
            System.out.println("No existing goals.");
            return;
        }
        // print all goals
        records.forEach(System.out::println);
    }
}
