package pl.dawidbasa.crediAnalyser.Credit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
	@NotNull(message = "*Specify the loan amount ,Can not be Empty")
	private Integer mortgageDebt;

	@Column(name = "mortgage_term")
	@NotNull(message = "*Specify the loan term ,Can not be Empty")
	private Integer mortgageTerm;

	@Column(name = "credit_margin")
	@NotNull(message = "*Specify the loan margin ,Can not be Empty")
	private Double creditMargin;

	@Column(name = "wibor")
	@NotNull(message = "*Specify the WIBOR ,Can not be Empty")
	private Double wibor;

	@Column(name = "commision_fee")
	@NotNull(message = "*Specify the WIBOR ,Can not be Empty")
	private Integer commisionFee;

	public Credit() {
		super();
	}

	public Credit(String mortgageName, Integer mortgageDebt, Integer mortgageTerm, Double creditMargin, Double wibor,
			Integer commisionFee) {
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

	public Integer getMortgageDebt() {
		return mortgageDebt;
	}

	public void setMortgageDebt(Integer mortgageDebt) {
		this.mortgageDebt = mortgageDebt;
	}

	public Integer getMortgageTerm() {
		return mortgageTerm;
	}

	public void setMortgageTerm(Integer mortgageTerm) {
		this.mortgageTerm = mortgageTerm;
	}

	public Double getCreditMargin() {
		return creditMargin;
	}

	public void setCreditMargin(Double creditMargin) {
		this.creditMargin = creditMargin;
	}

	public Double getWibor() {
		return wibor;
	}

	public void setWibor(Double wibor) {
		this.wibor = wibor;
	}

	public Integer getCommisionFee() {
		return commisionFee;
	}

	public void setCommisionFee(Integer commisionFee) {
		this.commisionFee = commisionFee;
	}

	@Override
	public String toString() {
		return "Credit [id=" + id + ", mortgageName=" + mortgageName + ", mortgageDebt=" + mortgageDebt
				+ ", mortgageTerm=" + mortgageTerm + ", creditMargin=" + creditMargin + ", wibor=" + wibor
				+ ", commisionFee=" + commisionFee + "]";
	}

}
