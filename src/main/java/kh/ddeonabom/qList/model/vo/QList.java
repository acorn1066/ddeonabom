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
	private String category;
	private String visibility;
	private int replyCount;
	private String reportStatus;
	
	public String getWriter() {
        if (this.writer != null && this.writer.contains("_")) {
            return "(탈퇴회원)";
        }
        return this.writer;
    }
}
