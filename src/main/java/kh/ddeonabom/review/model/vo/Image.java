package kh.ddeonabom.review.model.vo;

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
public class Image {
	private int imageNo;
    private String imagePath;
    private String fileName;
    private String renameFile;
    private int travelSubNo;

}
