package com.adhd.Olivia.enums;

public enum RegularMenstruation {
    REGULAR(0, "regular"),
    NOTREGULAR(1, "not regular"),
    NOTSURE(2, "not sure");

    private String description;
    private int id;
    RegularMenstruation(int id, String description){
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static RegularMenstruation getById(int id){
        RegularMenstruation response = null;
        RegularMenstruation regulars[] = RegularMenstruation.values();
        for(RegularMenstruation reg: regulars) {
            if (reg.getId() == id) {
                response = reg;
                break;
            }
        }
        return response;
    }
}
