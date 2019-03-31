package javafx;

import getinfo.SummarizeTime;
import hibernate.Admin;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDate;

class GetAdminPieDataService extends Service<Integer> {

    private Admin admin;
    private LocalDate dateFrom, dateTo;

    public GetAdminPieDataService(Admin admin, LocalDate dateFrom, LocalDate dateTo) {
        this.admin = admin;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task() {
            @Override
            protected Integer call() {
                return SummarizeTime.adminTimesDurationTotal(admin.getAdminLink(), dateFrom, dateTo);
            }
        };
    }
}