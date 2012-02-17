package co.earcos.budget.control;

import co.earcos.budget.model.MovementVO;
import co.earcos.budget.util.Constants.Account;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DayResumeData {

    private Map<Account, Double> accountTotal;
    private Map<Account, Double> regAccountVariation;
    private Map<Account, Double> newAccountVariation;

    public DayResumeData(MonthData monthData) {
        accountTotal = new EnumMap<Account, Double>(Account.class);
        regAccountVariation = new EnumMap<Account, Double>(Account.class);
        newAccountVariation = new EnumMap<Account, Double>(Account.class);

        for (Account account : Account.values()) {
            accountTotal.put(account, monthData.getAccountTotal(account));
        }
    }

    public void calculateVariation(List<MovementVO> movementList) {
        for (MovementVO movementVO : movementList) {
            modifyVariation(movementVO.getAccount(), movementVO);
        }
    }

    private void modifyVariation(Account account, MovementVO movementVO) {
        Double temp;

        if (movementVO.getId() == null) {
            temp = newAccountVariation.get(account);
            if (temp == null) {
                newAccountVariation.put(account, movementVO.getValue());
            } else {
                newAccountVariation.put(account, temp + movementVO.getValue());
            }
        } else {
            temp = regAccountVariation.get(account);
            if (temp == null) {
                regAccountVariation.put(account, movementVO.getValue());
            } else {
                regAccountVariation.put(account, temp + movementVO.getValue());
            }
        }
    }

    public double getAccountTotal(Account account) {
        Double total = accountTotal.get(account);
        Double newVariation = newAccountVariation.get(account);

        total = total == null ? 0.0 : total;
        newVariation = newVariation == null ? 0.0 : newVariation;

        return total + newVariation;
    }

    public double getAccountVariation(Account account) {
        Double regVariation = regAccountVariation.get(account);
        Double newVariation = newAccountVariation.get(account);

        regVariation = regVariation == null ? 0.0 : regVariation;
        newVariation = newVariation == null ? 0.0 : newVariation;

        return regVariation + newVariation;
    }
}
