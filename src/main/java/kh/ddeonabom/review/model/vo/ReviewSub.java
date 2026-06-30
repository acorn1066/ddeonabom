package kh.ddeonabom.review.model.vo;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
public class ReviewSub {
	private int travelSubNo;
    private String travelSubContent;
    private int travelSubSeq;
    private Date travelSubDate;
    private int travelNo;
    private int contentId;
    // 조회한 이미지 경로 목록
    private List<String> images;
    private int rating;    
    // 이미지 업로드 파일
    private List<MultipartFile> imageFiles;
    private Double lat;
    private Double lng;
    // 관광지이름
    private String contentTitle;
    // DB 테이블에는 없지만, JOIN 결과를 담기 위해 자바 객체에만 추가하는 필드!
    private String imagePath;
    private String kakaoPlaceId;

}
