class Subtask extends Task {
    private final Epic epic; // Ссылка на родительский эпик

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    // Getter для эпика
    public Epic getEpic() {
        return epic;
    }
}