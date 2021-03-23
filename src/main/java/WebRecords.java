import java.io.Serializable;
import java.net.URL;
import java.sql.Date;

public class WebRecords {

    private String Symbol;
    private String ISIN;
    private String Company;
    private String Description;
    private String DocType;
    private String Date;
    private String linkToFile;
    private String linkToFile2;


    public WebRecords() {
    }

    public WebRecords(String symbol, String ISIN, String company, String description,String date, String docType, String linkToFile) {
        Symbol = symbol;
        this.ISIN = ISIN;
        Company = company;
        Description = description;
        Date = date;
        DocType = docType;
        this.linkToFile = linkToFile;
    }

    public String getDate() {
        return Date;
    }

    public String getLinkToFile2() {
        return linkToFile2;
    }

    public WebRecords(String symbol, String ISIN, String company, String description, String date, String docType, String linkToFile, String linkToFile2) {
        Symbol = symbol;
        this.ISIN = ISIN;
        Company = company;
        Description = description;
        Date = date;
        DocType = docType;
        this.linkToFile = linkToFile;
        this.linkToFile2 = linkToFile2;
    }



    public String getSymbol() {
        return Symbol;
    }

    public String getISIN() {
        return ISIN;
    }

    public String getCompany() {
        return Company;
    }

    public String getDescription() {
        return Description;
    }

    public String getDocType() {
        return DocType;
    }

    public String getLinkToFile() {
        return linkToFile;
    }
}
