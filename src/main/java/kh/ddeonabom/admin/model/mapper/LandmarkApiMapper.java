package kh.ddeonabom.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kh.ddeonabom.admin.model.vo.ApiSyncLog;
import kh.ddeonabom.landmark.model.vo.Landmark;

@Mapper
public interface LandmarkApiMapper {

	ApiSyncLog selectSyncLog();

	void mergeLandmark(Landmark landmark);
	
	List<Integer> selectContentIdsForOverview(@Param("lastOverviewId") int lastOverviewId, @Param("limit") int limit);
	
	int updateOverview(@Param("contentId") int contentId, @Param("overview") String overview);

	int selectLandmarkCount();

	int selectOverviewCount();
	
	ApiSyncLog selectSyncLog(String apiKey);
	int updateSyncLog(ApiSyncLog syncLog);
	int insertSyncLog(ApiSyncLog syncLog);
	
	
}
