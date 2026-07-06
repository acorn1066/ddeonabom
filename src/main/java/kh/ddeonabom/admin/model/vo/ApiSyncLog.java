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
	private Integer logId;
    private Integer lastPage;
    private Integer totalPages;
    private Date collectSync;
    private Date updateSync;
    private String status;
    private Integer dailyCalls;
    private Date lastResetDate;
    private Integer lastOverviewId;
    private Integer count;
    private Integer overviewCount;
    private String apiKey;
}
