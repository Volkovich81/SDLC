public class Main {
    public static void main(String[] args) {
        View view = new View();
        Model model = new Model();
        InputDialog dialog = new InputDialog(view);
        new Controller(view, model, dialog);
    }
}