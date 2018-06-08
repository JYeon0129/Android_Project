package com.example.wkddu.android_project_mcm;

public class Type {
    int typeNum;
    String typeName;
    int typeColor;

    Type () {}

    Type (int typeNum, String typeName, int typeColor) {
        this.typeNum = typeNum;
        this.typeColor = typeColor;
        this.typeName = typeName;
    }

    public int getTypeNum() {
        return this.typeNum;
    }

    public int getTypeColor() {
        return this.typeColor;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }

    public void setTypeColor(int typeColor) {
        this.typeColor = typeColor;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
