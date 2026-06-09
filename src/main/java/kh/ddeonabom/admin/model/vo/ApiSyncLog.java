package kh.ddeonabom.admin.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiSyncLog {
	private int logId;
    private int lastPage;
    private int totalPages;
    private Date collectSync;
    private Date updateSync;
    private String status;
    private int dailyCalls;
    private Date lastResetDate;
    private int lastOverviewId;
    private int count;
    private int overviewCount;
    private String apiKey;
}
