package co.earcos.budget.control;

import co.earcos.budget.model.MovementVO;
import co.earcos.budget.util.Constants.Account;
import java.util.*;

public class DayData {

    private Calendar date;
    private Map<Account, Double> accountDayTotal;
    private List<MovementVO> movementList;
    private transient List<MovementVO> addedMovementList;
    private transient List<MovementVO> removedMovementList;

    public DayData(Date date) {
        this.date = Calendar.getInstance();
        this.date.setTime(date);

        accountDayTotal = new EnumMap<Account, Double>(Account.class);
        movementList = new ArrayList<MovementVO>();
        addedMovementList = new ArrayList<MovementVO>();
        removedMovementList = new ArrayList<MovementVO>();
    }

    public void setAccountDayTotal(Account account, double total) {
        accountDayTotal.put(account, total);
    }

    public double getAccountDayTotal(Account account) {
        Double total = accountDayTotal.get(account);
        return total == null ? 0.0 : total;
    }

    public List<MovementVO> getAddedMovementList() {
        return addedMovementList;
    }

    public void setAddedMovementList(List<MovementVO> addedMovementList) {
        this.addedMovementList = addedMovementList;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public List<MovementVO> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<MovementVO> movementList) {
        this.movementList = movementList;
    }

    public List<MovementVO> getRemovedMovementList() {
        return removedMovementList;
    }

    public void setRemovedMovementList(List<MovementVO> removedMovementList) {
        this.removedMovementList = removedMovementList;
    }

    public List<MovementVO> getAccountMovement(Account account){
        List<MovementVO> accountMovement = new ArrayList<MovementVO>();

        for(MovementVO mvo: movementList) {
            if(mvo.getAccount().equals(account)) {
                accountMovement.add(mvo);
            }
        }

        return accountMovement;
    }
}
