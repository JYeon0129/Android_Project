package com.example.wkddu.android_project_mcm;

public class Todo {
    // 할 일을 저장하는 친구인데 임시로 만든거라 맘대로 바꾸셔도 됩니다!
    String todoName; // 화면에 보일 약속의 이름입니다.
    int cost; // 예상 비용입니다.
    int type; // 술약속인지 밥약속인지 회식인지 등 타입을 지정합니다.

    public Todo(String todoName, int cost, int type) {
        this.todoName = todoName;
        this.cost = cost;
        this.type = type;
    }

    public String getTodoName() {
        return this.todoName;
    }

    public int getCost() {
        return this.cost;
    }

    public int getType() {
        return this.type;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setType(int type) {
        this.type = type;
    }
}
