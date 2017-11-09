package com.minivision.aop.faceset.rest;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.minivision.aop.faceset.domain.Face;
import com.minivision.aop.faceset.domain.FaceSet;
import com.minivision.aop.faceset.mvc.ex.ServiceException;
import com.minivision.aop.faceset.rest.param.faceset.FaceSearchParam;
import com.minivision.aop.faceset.rest.param.faceset.FacesetParam;
import com.minivision.aop.faceset.rest.result.RestResult;
import com.minivision.aop.faceset.rest.result.faceset.FaceSearchResult;
import com.minivision.aop.faceset.service.FaceService;
import com.minivision.aop.faceset.service.FaceSetService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1", method = RequestMethod.POST)
@Api(tags = "FacesetApi", value = "Faceset Apis")
public class FacesetApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(FacesetApi.class);

	@Autowired
	private FaceSetService faceSetService;

	@Autowired
	private FaceService faceService;

	@RequestMapping(value = "facesets")
	@ApiOperation(value = "人脸库查询", notes = "人脸库查询")
	public RestResult<List<FaceSet>> getFacesets() {
		List<FaceSet> list = faceSetService.findAll();
		return new RestResult<>(list);
	}

	@RequestMapping(value = "faces")
	@ApiOperation(value = "人脸库人脸查询", notes = "人脸库人脸查询")
	public RestResult<List<Face>> getFacesets(@ModelAttribute FacesetParam facesetParam) {
		Page<Face> faces = faceService.findByFacesetId(facesetParam);
		return new RestResult<>(faces.getContent());
	}

	@RequestMapping(value = "faceSearch")
	@ApiOperation(value = "人脸检索", notes = "对输入的人脸照片，和选择的人脸库进行比对，输出前N条相似的人")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "imgfile", paramType = "form", dataType = "file", required = true)})
	public RestResult<List<FaceSearchResult>> faceSearch(@ModelAttribute FaceSearchParam param) {
		List<FaceSearchResult> faceSearchResults = null;
		try {
			faceSearchResults = faceService.searchByPlat(param);
		} catch (ServiceException e) {
			return new RestResult<>(e.getMessage());
		}catch (Exception e){
			return new RestResult<>(e);
		}
		return new RestResult<>(faceSearchResults);
	}

}
