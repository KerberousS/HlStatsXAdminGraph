package javafx;

import getinfo.SummarizeTime;
import hibernate.Admin;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class GetAdminChartDataService extends Service<List<LocalDateTime>> {

    private Admin admin;
    private LocalDate dateFrom, dateTo;

    GetAdminChartDataService(Admin admin, LocalDate dateFrom, LocalDate dateTo) {
        this.admin = admin;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    protected Task<List<LocalDateTime>> createTask() {
        return new Task<>() {
            @Override
            protected List<LocalDateTime> call() {
                return SummarizeTime.getTimesListFromPeriod(admin.getAdminLink(), dateFrom, dateTo);
            }
        };
    }
}