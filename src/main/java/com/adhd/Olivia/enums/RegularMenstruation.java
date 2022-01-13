package com.adhd.Olivia.enums;

public enum RegularMenstruation {
    REGULAR("regular"),
    NOTREGULAR("not regular"),
    NOTSURE("not sure");

    private String description;

    RegularMenstruation(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static RegularMenstruation getById(int id){
		switch (id) {
			case 0: return RegularMenstruation.REGULAR;
			case 1: return RegularMenstruation.NOTREGULAR;
			case 2: return RegularMenstruation.NOTSURE;
			default: return null;
		}	
    }
}
