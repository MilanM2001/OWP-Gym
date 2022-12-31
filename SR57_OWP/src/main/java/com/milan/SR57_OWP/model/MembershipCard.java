package com.milan.SR57_OWP.model;

public class MembershipCard {

	private Long id;
	private Integer discount;
	private Double cardPoints;
	private String cardUsername;
	private String cardStatus; //enum
	
	public MembershipCard() {}

	public MembershipCard(Long id, Integer discount, Double cardPoints, String cardUsername, String cardStatus) {
		super();
		this.id = id;
		this.discount = discount;
		this.cardPoints = cardPoints;
		this.cardUsername = cardUsername;
		this.cardStatus = cardStatus;
	}
	
	public MembershipCard(Integer discount, Double cardPoints, String cardUsername, String cardStatus) {
		super();
		this.discount = discount;
		this.cardPoints = cardPoints;
		this.cardUsername = cardUsername;
		this.cardStatus = cardStatus;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MembershipCard other = (MembershipCard) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Double getCardPoints() {
		return cardPoints;
	}

	public void setCardPoints(Double cardPoints) {
		this.cardPoints = cardPoints;
	}

	public String getCardUsername() {
		return cardUsername;
	}

	public void setCardUsername(String cardUsername) {
		this.cardUsername = cardUsername;
	}
	
	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	@Override
	public String toString() {
		return "MembershipCard [discount=" + discount + ", cardPoints=" + cardPoints + ", cardUsername=" + cardUsername
				+ ", cardStatus=" + cardStatus + "]";
	}

}
