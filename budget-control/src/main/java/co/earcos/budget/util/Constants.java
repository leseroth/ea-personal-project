package co.earcos.budget.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author earcos
 */
public class Constants {

  public static final int GAP = 4;

  public enum Account {

    CASH("ef", "Efectivo"), SAVING("ah", "Ahorro"),
    UNIACCION("un", "Uniaccion"), FIDUCUENTA("fi", "Fiducuenta"), INDEACCION("in", "Indeaccion"),
    AMEX("cp", "AMEX Pesos"), AMEXUSD("cd", "AMEX Dolares", "en", "US"), CMR("cm", "CMR");
    private String id;
    private String label;
    private NumberFormat currencyFormat;

    Account(String ids, String labels) {
      this(ids, labels, "es", "CO");
    }

    Account(String ids, String labels, String language, String country) {
      id = ids;
      label = labels;
      currencyFormat = NumberFormat.getCurrencyInstance(new Locale(language, country));
    }

    public String getId() {
      return id;
    }

    public String getLabel() {
      return label;
    }

    public NumberFormat getCurrencyFormat() {
      return currencyFormat;
    }

    public boolean isCreditCard() {
      return this == AMEX || this == AMEXUSD || this == CMR;
    }
  }

  public enum Concept {

    FOOD("Comida"), TRANSPORT("Transporte"), OTHERS("Otros"), INTERESTS("Intereses"),
    LOAN_INTEREST("Intereses Prestamo"), LOAN("Prestamo"), MOVEMENT("Movimiento"), SALARY("Sueldo"),
    SERVICES("Servicios"), RENT("Arriendo"), PAYMENT("Pago"), CELLPHONE("Celular"),
    CLOTHES("Ropa"), MOVIES("Cine"), UNIVERSITY("Universidad"), TRAVEL("Viaje"),
    IVA_DEVOLUTION("Devolucion de iva"), GROUPON("Groupon"), XD_APPS("XD Apps");
    private String label;

    Concept(String labels) {
      label = labels;
    }

    public String getLabel() {
      return label;
    }
  }
}
