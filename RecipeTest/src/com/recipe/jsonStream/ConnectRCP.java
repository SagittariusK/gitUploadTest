package com.recipe.jsonStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.recipe.common.HttpClientUtil;
import com.recipe.common.ObjectUtils;
import com.recipe.vo.RCP_CrseVO;
import com.recipe.vo.RCP_InfoVO;
import com.recipe.vo.RCP_IrdntVO;

import com.recipe.common.CatchUtils;

public class ConnectRCP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ObjectUtils objUtils = new ObjectUtils();
	
	private String url = "http://211.237.50.150:7080/openapi";
	private String api_key = "8c75e56a28a4218153e84f891c91fddc6a9436ec161c290b72199b79883a9d2a";
	private String type = "json";
	
	private String grid_Info = "Grid_20150827000000000226_1";
	private String grid_Irdnt = "Grid_20150827000000000227_1";
	private String grid_Crse = "Grid_20150827000000000228_1";
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getInfo(int start_index, int end_index, HashMap<String, Object> paramHashMap) {
		HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
		
		// API URL 호출 및 JSON결과 HashMap 리턴
		try {
			returnHashMap = getRecipejsonStream(grid_Info, start_index, end_index, paramHashMap);
		} catch (Exception e) {
			logger.error("get jsonStream(API URL Call) Error:");
			new CatchUtils(e);
		}
		
		List<RCP_InfoVO> infoList = new ArrayList<RCP_InfoVO>();
		
		String code = objUtils.toStr(objUtils.toHashMap(returnHashMap, "recipeResultHashMap").get("code"));
		String message = objUtils.toStr(objUtils.toHashMap(returnHashMap, "recipeResultHashMap").get("message"));
		
		if("INFO-000".equals(code) && "정상 처리되었습니다.".equals(message)) {
			// API URL 호출 결과 리스트 리턴(HashMap)
			List<HashMap<String, Object>> recipeResultList = getRecipeList(objUtils.toHashMap(returnHashMap, "jsonHashMap"), grid_Info);
			try {
				// HashMap 형식의 List -> VO 형식의 List 변환
				infoList = (List<RCP_InfoVO>) this.toVoList(recipeResultList, "Info");
			} catch (Exception e) {
				logger.error("ListMap to ListVO Error:");
				new CatchUtils(e);
			}
			returnHashMap.put("infoList", infoList);
		}
		
		return returnHashMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getIrdnt(int start_index, int end_index, HashMap<String, Object> paramHashMap) {
		HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
		
		// API URL 호출 및 JSON결과 HashMap 리턴
		try {
			returnHashMap = getRecipejsonStream(grid_Irdnt, start_index, end_index, paramHashMap);
		} catch (Exception e) {
			logger.error("get jsonStream(API URL Call) Error:");
			new CatchUtils(e);
		}
		
		List<RCP_IrdntVO> irdntList = new ArrayList<RCP_IrdntVO>();
		
		String code = objUtils.toStr(objUtils.toHashMap(returnHashMap, "recipeResultHashMap").get("code"));
		String message = objUtils.toStr(objUtils.toHashMap(returnHashMap, "recipeResultHashMap").get("message"));
		
		if("INFO-000".equals(code) && "정상 처리되었습니다.".equals(message)) {
			// API URL 호출 결과 리스트 리턴(HashMap)
			List<HashMap<String, Object>> recipeResultList = getRecipeList(objUtils.toHashMap(returnHashMap, "jsonHashMap"), grid_Irdnt);
			try {
				// HashMap 형식의 List -> VO 형식의 List 변환
				irdntList = (List<RCP_IrdntVO>) this.toVoList(recipeResultList, "Irdnt");
			} catch (Exception e) {
				logger.error("ListMap to ListVO Error:");
				new CatchUtils(e);
			}
			returnHashMap.put("irdntList", irdntList);
		}
		
		return returnHashMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getCrse(int start_index, int end_index, HashMap<String, Object> paramHashMap) {
		HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
		
		// API URL 호출 및 JSON결과 HashMap 리턴
		try {
			returnHashMap = getRecipejsonStream(grid_Crse, start_index, end_index, paramHashMap);
		} catch (Exception e) {
			logger.error("get jsonStream(API URL Call) Error:");
			new CatchUtils(e);
		}
		
		List<RCP_CrseVO> crseList = new ArrayList<RCP_CrseVO>();
		
		String code = objUtils.toStr(objUtils.toHashMap(returnHashMap, "recipeResultHashMap").get("code"));
		String message = objUtils.toStr(objUtils.toHashMap(returnHashMap, "recipeResultHashMap").get("message"));
		
		if("INFO-000".equals(code) && "정상 처리되었습니다.".equals(message)) {
			// API URL 호출 결과 리스트 리턴(HashMap)
			List<HashMap<String, Object>> recipeResultList = getRecipeList(objUtils.toHashMap(returnHashMap, "jsonHashMap"), grid_Crse);
			try {
				// HashMap 형식의 List -> VO 형식의 List 변환
				crseList = (List<RCP_CrseVO>) this.toVoList(recipeResultList, "Crse");
			} catch (Exception e) {
				logger.error("ListMap to ListVO Error:");
				new CatchUtils(e);
			}
			returnHashMap.put("crseList", crseList);
		}
		
		return returnHashMap;
	}
	
	public List<?> toVoList(List<HashMap<String, Object>> list, String taskGubun) {
		List<Object> returnList = new ArrayList<>();
		try {
			for (HashMap<String, Object> hashMap : list) {
				
				if("Info".equals(taskGubun)) {
					RCP_InfoVO rcp_InfoVo = new RCP_InfoVO();
					// hashMap - > rcp_IrdntVo 변환
					objUtils.toObject(objUtils.toHashmapLower(hashMap), rcp_InfoVo);
					returnList.add(rcp_InfoVo);
				} else if("Irdnt".equals(taskGubun)) {
					RCP_IrdntVO rcp_IrdntVo = new RCP_IrdntVO();
					// hashMap - > rcp_IrdntVo 변환
					objUtils.toObject(objUtils.toHashmapLower(hashMap), rcp_IrdntVo);
					returnList.add(rcp_IrdntVo);
				} else if("Crse".equals(taskGubun)) {
					RCP_CrseVO rcp_CrseVo = new RCP_CrseVO();
					// hashMap - > rcp_IrdntVo 변환
					objUtils.toObject(objUtils.toHashmapLower(hashMap), rcp_CrseVo);
					returnList.add(rcp_CrseVo);
				}
			}
		} catch (Exception e) {
			logger.error("ListMap to ListVO Error:");
			new CatchUtils(e);
		}
		
		return returnList;
	}
	
	public HashMap<String, Object> getRecipejsonStream(String gridStr, int start_index, int end_index, HashMap<String, Object> paramHashMap) {
		HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
		
		// start_index 가 0일경우 기본값 0
		start_index = (0 == objUtils.toInt(start_index)) ? 1 : start_index;
		
		// end_index 가 0이거나 999보다 클 경우 기본값 5
		end_index = (0 == objUtils.toInt(end_index) || 999 < end_index) ? 5 : end_index;
		
		// 더미용 파라미터
		String parameter = "?dumyParam=";
		try {
			// HashMap의 각 키, 값 변수에 추가
			if(!paramHashMap.isEmpty()) {
				for (String mapkey : paramHashMap.keySet()) {
					if(!"".equals("" + paramHashMap.get(mapkey)) && null != paramHashMap.get(mapkey)) {
						parameter += "&" + mapkey.toUpperCase() + "=" + paramHashMap.get(mapkey);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Parameter Create Error:");
			new CatchUtils(e);
		}
		
		// API 호출용 URL Path 생성
		String path = "/" + api_key + "/" + type + "/" + gridStr + "/" + start_index + "/" + end_index + "";
		
		// API URL 호출
		String jsonStream = HttpClientUtil.get(url + path + parameter);
		logger.info("Call URL:");
		logger.info(url + path + parameter);
		
		// JsonStream -> HashMap 변환
		HashMap<String, Object> jsonHashMap = objUtils.JsonToHashMap(jsonStream);
		returnHashMap.put("jsonHashMap", jsonHashMap);
		
		System.out.println("jsonHashMap: " + jsonHashMap.toString());
		
		// API URL 호출 결과 사이즈
		int totalCnt = getRecipeTotalCnt(jsonHashMap, gridStr);
		returnHashMap.put("totalCnt", totalCnt);
		
		// API URL 호출 결과 상태 리턴
		HashMap<String, Object> recipeResultHashMap = getRecipeResult(jsonHashMap, gridStr);
		returnHashMap.put("recipeResultHashMap", recipeResultHashMap);

		
		return returnHashMap;
	}
	
	public int getRecipeTotalCnt(HashMap<String, Object> jsonHashMap, String grid) {
		// 호출 결과 사이즈 Map 분리
		HashMap<String, Object> getData = new HashMap<String, Object>();
		try {
			getData = objUtils.toHashMap(jsonHashMap, grid);
		} catch (Exception e) {
			logger.error("Parameter Create Error:");
			new CatchUtils(e);
		}
		return objUtils.toInt(getData.get("totalCnt"));
	}
	
	public HashMap<String, Object> getRecipeResult(HashMap<String, Object> jsonHashMap, String grid){
		HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
		// 호출 결과 상태 Map 분리
		HashMap<String, Object> getData = new HashMap<String, Object>();
		try {
			getData = objUtils.toHashMap(jsonHashMap, grid);
		} catch (Exception e) {
			logger.error("Parameter Create Error:");
			new CatchUtils(e);
		}
		
		String code = "";
		String message = "";
		if(null != getData && !getData.isEmpty()) {
			code = objUtils.toStr(objUtils.toHashMap(getData, "result").get("code"));
			message = objUtils.toStr(objUtils.toHashMap(getData, "result").get("message"));
		}else {
			getData = objUtils.toHashMap(jsonHashMap, "result");
			code = objUtils.toStr(getData.get("code"));
			message = objUtils.toStr(getData.get("message"));
		}
		returnHashMap.put("code", code);
		returnHashMap.put("message", message);
		
		logger.info("API URL 호출 결과 ->");
		logger.info("code: " + code + ", message: " + message);
		
		return returnHashMap;
	}
	
	public List<HashMap<String, Object>> getRecipeList(HashMap<String, Object> jsonHashMap, String grid){
		// 호출 결과 상태 Map 분리
		HashMap<String, Object> getData = new HashMap<String, Object>();
		try {
			getData = objUtils.toHashMap(jsonHashMap, grid);
		} catch (Exception e) {
			logger.error("Parameter Create Error:");
			new CatchUtils(e);
		}
		
		return objUtils.toListInHashMap(getData, "row");
	}
	
	public HashMap<String, Object> toResultHashMap(HashMap<String, Object> resultHashMap){
		HashMap<String, Object> returnHashMap = new HashMap<String, Object>();
		
		returnHashMap = objUtils.toHashMap(resultHashMap, "recipeResultHashMap");
				
		return returnHashMap;
	}
	
	@SuppressWarnings("unchecked")
	public List<RCP_InfoVO> toListInInfoVo(HashMap<String, Object> resultHashMap){
		List<RCP_InfoVO> infoList = new ArrayList<RCP_InfoVO>();
		infoList = (List<RCP_InfoVO>) resultHashMap.get("infoList");
		return infoList;
	}
	
	@SuppressWarnings("unchecked")
	public List<RCP_IrdntVO> toListInIrdntVo(HashMap<String, Object> resultHashMap){
		List<RCP_IrdntVO> irdntList = new ArrayList<RCP_IrdntVO>();
		irdntList = (List<RCP_IrdntVO>) resultHashMap.get("irdntList");
		return irdntList;
	}
	
	@SuppressWarnings("unchecked")
	public List<RCP_CrseVO> toListInCrseVo(HashMap<String, Object> resultHashMap){
		List<RCP_CrseVO> crseList = new ArrayList<RCP_CrseVO>();
		crseList = (List<RCP_CrseVO>) resultHashMap.get("crseList");
		return crseList;
	}
}
