package kh.ddeonabom.common.paging;

public class Pagination {
	public static PageInfo getPageInfo(int pageLimit, int currentPage, int listCount, int boardLimit) {
		int maxPage = (int) Math.ceil((double)listCount/boardLimit);
		int startPage = (currentPage - 1)/pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if(maxPage < endPage) {
			endPage = maxPage;
		}
		return new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
	}
}
