package kh.ddeonabom.reply.model.vo;

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
public class Reply {
	private int replyNo;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private String status;
	private String postBoard;
	private int postNo;
	private int memberNo;
	private String writer;   // MEMBER 테이블 JOIN으로 가져올 nickname
	private String reportStatus;
}
