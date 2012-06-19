package co.earcos.util.work;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UpdateEndpointSoapUi {

    private static final String FILE_PATH = "C:\\workspace\\icbs-bus-simulator-web\\src\\test\\resources\\soap-ui\\";
    private static final String FILE_SUFIX = "-soapui-project.xml";
    private Map<String, File> fileMap;
    private int counter;

    public static void main(String... args) {
        UpdateEndpointSoapUi ue = new UpdateEndpointSoapUi();
        ue.execute();
    }

    private void execute() {
        fileMap = new HashMap<String, File>();
        loadFiles();
        replaceEndpointInFiles();
    }

    private void loadFiles() {
        for (String[] info : context) {
            File soapUi = new File(FILE_PATH + info[1] + FILE_SUFIX);
            if (!soapUi.exists()) {
                soapUi = new File(FILE_PATH + info[1] + "-wsdl" + FILE_SUFIX);
                if (!soapUi.exists()) {
                    if (info[1].startsWith("PSEIFXServer")) {
                        soapUi = new File(FILE_PATH + "PSE-IFXServer-soapui-project.xml");
                    } else {
                        System.err.println("No se encuentra a: " + soapUi.getAbsolutePath());
                    }
                }
            }
            fileMap.put(info[1], soapUi);
        }
    }

    private void replaceEndpointInFiles() {
        for (String[] info : context) {
            File soapUi = fileMap.get(info[1]);
            String soapUiNewContent = updateEndpoint(soapUi, info[0], info[1]);
            updateFile(soapUi, soapUiNewContent);
        }
        System.out.println("Total de reemplazos: " + counter);
    }

    private String updateEndpoint(File file, String context, String service) {
        StringBuilder newFile = new StringBuilder();
        // Servicio
        String serviceDeclaration = "/icbs-bus-simulator-web/" + service;
        String newServiceDeclaration = "/icbs-bus-simulator-web/" + context + "/" + service;

        // Binding <BalanceInquiry>ImplServiceSoapBinding = <AccountActivityInquiry>SvcSoapBinding
        // String serviceDeclaration = service+"ImplServiceSoapBinding";
        // String newServiceDeclaration = service+"SvcSoapBinding";

        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            StringBuilder sb = new StringBuilder();

            while ((strLine = br.readLine()) != null) {
                if (strLine.indexOf(serviceDeclaration) != -1) {
                    strLine = strLine.replaceAll(serviceDeclaration, newServiceDeclaration);
                    counter++;
                }

                newFile.append(strLine);
                newFile.append("\n");
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return newFile.toString();
    }

    private void updateFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(content);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    private String[][] context = {{"Accounts", "BalanceInquiry"},
        {"Accounts", "AccountActivityInquiry"},
        {"Accounts", "BouncedCheckInquiry"},
        {"Accounts", "BankAccountStatement"},
        {"Accounts", "AuthorizedUserCardInquiry"},
        {"Accounts", "LoanInquiry"},
        {"Accounts", "ElectronicStatements"},
        {"Accounts", "AccountStatusAdmin"},
        {"Accounts", "TransferStatement"},
        {"Accounts", "OrderCheckBook"},
        {"Accounts", "MediaFeeInquiry"},
        {"Accounts", "PaymentCheck"},
        {"Accounts", "CheckInquiry"},
        {"Accounts", "TaxCertificate"},
        {"Accounts", "CheckStatus"},
        {"Accounts", "CheckActivation"},
        {"Accounts", "CheckStatusBAVV"},
        {"Accounts", "CreditCardInquiry"},
        {"Accounts", "CCQuotaDistribution"},
        {"Accounts", "CreditCardReissue"},
        {"Payments", "DirectPaymentInquiry"},
        {"Payments", "Remittances"},
        {"Payments", "FileDataAdmin"},
        {"Payments", "PSEPrivateServices"},
        {"Payments", "CustomerDirectPmtInquiry"},
        {"Payments", "CitiesInquiry"},
        {"Payments", "BillerInquiry"},
        {"Payments", "BillingInquiry"},
        {"Payments", "BillPayment"},
        {"Payments", "NotificationAdm"},
        {"Payments", "PayeeAdmin"},
        {"Payments", "TaxPayment"},
        {"Payments", "HistPmtInquiry"},
        {"Payments", "DetailRecPmtInquiry"},
        {"Payments", "CreditCardPaymentDirected"},
        {"Payments", "LoanPayment"},
        {"Payments", "CreditCardPayment"},
        {"Payments", "TaxPaymentDIANBBOG"},
        {"Payments", "PSEServicesBBOG"},
        {"Payments", "ForeignExchangeAdmin"},
        {"Payments", "RecPmtAdmin"},
        {"Payments", "BillingDelete"},
        {"Payments", "NoBilledInq"},
        {"Payments", "NoBillerEntityInquiry"},
        {"Payments", "BillerAdmin"},
        {"Payments", "CompanyInquiry"},
        {"Customers", "CustomerInquiry"},
        {"Customers", "CustomerAuthentication"},
        {"Customers", "TokenInquiry"},
        {"Customers", "DigipassUserAdmin"},
        {"Customers", "TokenStatusAdmin"},
        {"Customers", "TokenAssignation"},
        {"Customers", "TokenAssignRange"},
        {"Customers", "TokenReassign"},
        {"Customers", "TokenAssignReverse"},
        {"Customers", "TokenUnassign"},
        {"Customers", "CustomerProductInquiry"},
        {"Customers", "PasswordChange"},
        {"Customers", "BillingPlan"},
        {"Customers", "UserStatus"},
        {"Customers", "CustomerStatus"},
        {"Customers", "VirtualPayBOG"},
        {"Customers", "CustomerAuthenticationPBIT"},
        {"Support", "FileRequestSocialSecurity"},
        {"Support", "FileStatus"},
        {"Support", "MulticashFile"},
        {"Support", "FIleStatusBBOG"},
        {"Support", "FileOtherChannels"},
        {"Support", "UploadFiles"},
        {"Transfers", "MoneyTransferInquiry"},
        {"Transfers", "SEBRATransfer"},
        {"Transfers", "PartyTransfer"},
        {"Transfers", "MoneyTransfer"},
        {"Transfers", "BranchInquiry"},
        {"Transfers", "TransferAccountsAdmin"},
        {"Transfers", "PartyTransferOCC"},
        {"Transfers", "AuthorizedTransferDebit"},
        {"Transfers", "FundsTransfer"},
        {"Transfers", "CashAdvance"},
        {"ProdsChnsMngt", "Notification"},
        {"ProdsChnsMngt", "DestinationAlertAdmin"},
        {"ProdsChnsMngt", "DestinationAlertInquiry"},
        {"ProdsChnsMngt", "AlertAdminHost"},
        {"services", "PSEIFXServerFacade"}};
}
