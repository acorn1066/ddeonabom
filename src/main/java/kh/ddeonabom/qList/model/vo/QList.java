package kh.ddeonabom.qList.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QList {
	private int qNo;
	private String title;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private int count;
	private String status;
	private int memberNo;
	private String writer;
	private String writerStatus;   // MEMBER.STATUS 조인 결과 (Y/N/B) - 화면 표시 가공용
	private String category;
	private String visibility;
	private int replyCount;
	private String reportStatus;

	public String getDisplayWriter() {
        if ("N".equals(this.writerStatus)) {
            return "(탈퇴회원)";
        }
        return this.writer;
    }
}
