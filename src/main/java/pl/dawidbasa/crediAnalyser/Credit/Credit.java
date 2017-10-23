package pl.dawidbasa.crediAnalyser.Credit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Credit {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "credi_id")
	private long id;

	@Column(name = "name")
	@NotEmpty(message = "*Please provide mortgage name")
	private String mortgageName;

	@Column(name = "mortgage_debt")
	private int mortgageDebt;

	@Column(name = "mortgage_term")
	private int mortgageTerm;

	@Column(name = "credit_margin")
	private double creditMargin;

	@Column(name = "wibor")
	private double wibor;

	@Column(name = "commision_fee")
	private double commisionFee;

	public Credit() {
		super();
	}

	public Credit(String mortgageName, int mortgageDebt, int mortgageTerm, double creditMargin, double wibor,
			double commisionFee) {
		super();
		this.mortgageName = mortgageName;
		this.mortgageDebt = mortgageDebt;
		this.mortgageTerm = mortgageTerm;
		this.creditMargin = creditMargin;
		this.wibor = wibor;
		this.commisionFee = commisionFee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMortgageName() {
		return mortgageName;
	}

	public void setMortgageName(String mortgageName) {
		this.mortgageName = mortgageName;
	}

	public int getMortgageDebt() {
		return mortgageDebt;
	}

	public void setMortgageDebt(int mortgageDebt) {
		this.mortgageDebt = mortgageDebt;
	}

	public int getMortgageTerm() {
		return mortgageTerm;
	}

	public void setMortgageTerm(int mortgageTerm) {
		this.mortgageTerm = mortgageTerm;
	}

	public double getCreditMargin() {
		return creditMargin;
	}

	public void setCreditMargin(double creditMargin) {
		this.creditMargin = creditMargin;
	}

	public double getWibor() {
		return wibor;
	}

	public void setWibor(double wibor) {
		this.wibor = wibor;
	}

	public double getCommisionFee() {
		return commisionFee;
	}

	public void setCommisionFee(double commisionFee) {
		this.commisionFee = commisionFee;
	}

	@Override
	public String toString() {
		return "Credit [id=" + id + ", mortgageName=" + mortgageName + ", mortgageDebt=" + mortgageDebt
				+ ", mortgageTerm=" + mortgageTerm + ", creditMargin=" + creditMargin + ", wibor=" + wibor
				+ ", commisionFee=" + commisionFee + "]";
	}

}
