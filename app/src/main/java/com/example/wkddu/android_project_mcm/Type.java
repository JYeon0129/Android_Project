package com.example.wkddu.android_project_mcm;

public class Type {
    String typeName;
    int typeColor;

    Type () {}

    Type (String typeName, int typeColor) {
        this.typeColor = typeColor;
        this.typeName = typeName;
    }

    public int getTypeColor() {
        return this.typeColor;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeColor(int typeColor) {
        this.typeColor = typeColor;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
