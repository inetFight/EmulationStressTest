package Sort.Emulation.Model;

import java.util.Date;

public class RootElementLogicModel {
	private String barcode1;
	private String barcode2;
	private String barcode3;
	private String barcode4;
	private String barcode5;
	private Date SORTREQTIME;
	private Date SORTRPLTIME;
	private Date SORTRPTTIME;
	private Date SORTACKTIME;
	private String DID;
	
	
	
	public RootElementLogicModel(String barcode1) {
		super();
		this.barcode1 = barcode1;
	}
	

	public RootElementLogicModel(String barcode1, String barcode2) {
		super();
		this.barcode1 = barcode1;
		this.barcode2 = barcode2;
	}

	
	public RootElementLogicModel(String barcode1, String barcode2, String barcode3) {
		super();
		this.barcode1 = barcode1;
		this.barcode2 = barcode2;
		this.barcode3 = barcode3;
	}

	
	public RootElementLogicModel(String barcode1, String barcode2, String barcode3, String barcode4) {
		super();
		this.barcode1 = barcode1;
		this.barcode2 = barcode2;
		this.barcode3 = barcode3;
		this.barcode4 = barcode4;
	}


	public RootElementLogicModel(String barcode1, String barcode2, String barcode3, String barcode4, String barcode5) {
		super();
		this.barcode1 = barcode1;
		this.barcode2 = barcode2;
		this.barcode3 = barcode3;
		this.barcode4 = barcode4;
		this.barcode5 = barcode5;
	}
	public String getBarcode1() {
		return barcode1;
	}
	public void setBarcode1(String barcode1) {
		this.barcode1 = barcode1;
	}
	public String getBarcode2() {
		return barcode2;
	}
	public void setBarcode2(String barcode2) {
		this.barcode2 = barcode2;
	}
	public String getBarcode3() {
		return barcode3;
	}
	public void setBarcode3(String barcode3) {
		this.barcode3 = barcode3;
	}
	public String getBarcode4() {
		return barcode4;
	}
	public void setBarcode4(String barcode4) {
		this.barcode4 = barcode4;
	}
	public String getBarcode5() {
		return barcode5;
	}
	public void setBarcode5(String barcode5) {
		this.barcode5 = barcode5;
	}


	public Date getSORTREQTIME() {
		return SORTREQTIME;
	}


	public void setSORTREQTIME(Date sORTREQTIME) {
		SORTREQTIME = sORTREQTIME;
	}


	public Date getSORTRPLTIME() {
		return SORTRPLTIME;
	}


	public void setSORTRPLTIME(Date sORTRPLTIME) {
		SORTRPLTIME = sORTRPLTIME;
	}


	public Date getSORTRPTTIME() {
		return SORTRPTTIME;
	}


	public void setSORTRPTTIME(Date sORTRPTTIME) {
		SORTRPTTIME = sORTRPTTIME;
	}


	public Date getSORTACKTIME() {
		return SORTACKTIME;
	}


	public void setSORTACKTIME(Date sORTACKTIME) {
		SORTACKTIME = sORTACKTIME;
	}


	public String getDID() {
		return DID;
	}


	public void setDID(String dID) {
		DID = dID;
	}
	
}
