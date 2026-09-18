public class Controller {
    private String lastDate = "";

    public Controller(View view, Model model, InputDialog dialog) {
        view.setInputButtonListener(e -> {
            if (!lastDate.isEmpty()) {
                dialog.setDateInput(lastDate);
            }
            dialog.showDialog();
        });

        dialog.setCalculateListener(e -> {
            try {
                String date = dialog.getDateInput();
                if (date == null || date.trim().isEmpty()) {
                    dialog.showError("Пожалуйста, введите дату!");
                    return;
                }

                lastDate = date;

                long population = model.calculatePopulation(date);

                String formatted = String.format("%,d", population);
                view.updateResult(population);
                view.updateLastInput(date);
                view.saveData(date, formatted);

                dialog.closeDialog();

            } catch (Exception ex) {
                dialog.showError(ex.getMessage());
            }
        });
    }
}