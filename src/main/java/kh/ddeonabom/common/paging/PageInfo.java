package kh.ddeonabom.common.paging;

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
public class PageInfo {
	private Integer currentPage; 
	private Integer listCount;
	private Integer pageLimit;
	private Integer maxPage;
	private Integer startPage;
	private Integer endPage;
	private Integer boardLimit;
}
