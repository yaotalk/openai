package com.minivision.aop.face.rest;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.face.rest.param.faceset.AddFaceParam;
import com.minivision.aop.face.rest.param.faceset.RemoveFaceParam;
import com.minivision.aop.face.rest.param.faceset.SetCreateParam;
import com.minivision.aop.face.rest.param.faceset.SetDeleteParam;
import com.minivision.aop.face.rest.param.faceset.SetDetailParam;
import com.minivision.aop.face.rest.param.faceset.SetListParam;
import com.minivision.aop.face.rest.param.faceset.SetMergeParam;
import com.minivision.aop.face.rest.param.faceset.SetModifyParam;
import com.minivision.aop.face.rest.result.faceset.AddFaceResult;
import com.minivision.aop.face.rest.result.faceset.RemoveFaceResult;
import com.minivision.aop.face.rest.result.faceset.SetCreateResult;
import com.minivision.aop.face.rest.result.faceset.SetDeleteResult;
import com.minivision.aop.face.rest.result.faceset.SetDetailResult;
import com.minivision.aop.face.rest.result.faceset.SetListResult;
import com.minivision.aop.face.rest.result.faceset.SetMergeResult;
import com.minivision.aop.face.rest.result.faceset.SetModifyResult;
import com.minivision.aop.face.service.FaceSetService;
import com.minivision.aop.face.service.ex.FacePlatException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/v1/faceset", method = RequestMethod.POST)
@Api(tags = "FaceSetApi", value = "FaceSet Apis")
public class FaceSetApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FaceSetApi.class);
	
	@Autowired
	private FaceSetService faceSetService;
	
	/**
	 * 创建人脸库
	 * @param param
	 * @return
	 * @throws FacePlatException
	 */
	@RequestMapping(value = "create")
	@ApiOperation(value="create", notes="创建人脸库")
	@HystrixCommand
	public RestResult<SetCreateResult> create(@Valid @ModelAttribute SetCreateParam param) throws FacePlatException {
		LOGGER.info("create faceset api");
		RestResult<SetCreateResult> result = new RestResult<>();
		result.setData(faceSetService.create(param.getOwner(), param.getOuterId(), param.getDisplayName(), param.getCapacity()));
		return result;
	}

	/**
	 * 添加人脸
	 * @param param
	 * @return
	 * @throws FacePlatException
	 */
	@RequestMapping(value = "addface")
	@ApiOperation(value="addface", notes="添加人脸")
	@HystrixCommand
	public RestResult<AddFaceResult> addFace(@Valid @ModelAttribute AddFaceParam param) throws FacePlatException {
		LOGGER.info("add faces api");
		RestResult<AddFaceResult> result = new RestResult<>();
		result.setData(faceSetService.addFace(param.getFacesetToken(), param.getFaceTokens().split(",")));
		return result;
	}
	
	/**
	 * 删除人脸库
	 * @param param
	 * @return
	 * @throws FacePlatException 
	 */
	@RequestMapping(value = "delete")
	@ApiOperation(value="delete", notes="删除人脸库")
	@HystrixCommand
	public RestResult<SetDeleteResult> delete(@Valid @ModelAttribute SetDeleteParam param) throws FacePlatException {
		LOGGER.info("delete faceset api");
		RestResult<SetDeleteResult> result = new RestResult<>();
		result.setData(faceSetService.delete(param.getFacesetToken(), param.isForce()));
		return result;
	}

	/**
	 * 删除人脸
	 * @param param
	 * @return
	 * @throws FacePlatException
	 */
	@RequestMapping(value = "removeface")
	@ApiOperation(value="remove", notes="删除人脸")
	@HystrixCommand
	public RestResult<RemoveFaceResult> removeFace(@Valid @ModelAttribute RemoveFaceParam param) throws FacePlatException {
		LOGGER.info("remove face api");
		RestResult<RemoveFaceResult> result = new RestResult<>();
		result.setData(faceSetService.removeFace(param.getFacesetToken(), param.getFaceTokens().split(",")));
		return result;
	}
	
	/**
	 * 修改人脸库
	 * @param param
	 * @return
	 * @throws FacePlatException 
	 */
	@RequestMapping(value = "modify")
	@ApiOperation(value="modify", notes="修改人脸库")
	@HystrixCommand
	public RestResult<SetModifyResult> modify(@Valid @ModelAttribute SetModifyParam param) throws FacePlatException {
		LOGGER.info("modify faceset api");
		RestResult<SetModifyResult> result = new RestResult<>();
		result.setData(faceSetService.modify(param.getFacesetToken(), param.getDisplayName(), param.getCapacity()));
		return result;
	}
	
	/**
     * 查看人脸库列表
     * @param param
     * @return
     * @throws FacePlatException 
     */
    @RequestMapping(value = "list")
    @ApiOperation(value="list", notes="查询人脸列表")
    @HystrixCommand
	public RestResult<SetListResult> getFaceSetList(@Valid @ModelAttribute SetListParam param) throws FacePlatException {
	  LOGGER.info("get faceset list api");
	  RestResult<SetListResult> result = new RestResult<>();
	  SetListResult data = faceSetService.getFaceSetList(param.getOffset(), param.getLimit());
	  result.setData(data);
	  return result;
	}
	
	/**
	 * 查看人脸库信息
	 * @param param
	 * @return
	 * @throws FacePlatException 
	 */
	@RequestMapping(value = "getdetail")
	@ApiOperation(value="detail", notes="查询人脸库")
	@HystrixCommand
	public RestResult<SetDetailResult> getdetail(@Valid @ModelAttribute SetDetailParam param) throws FacePlatException {
		LOGGER.info("get faceset detail api");
		RestResult<SetDetailResult> result = new RestResult<>();
		result.setData(faceSetService.getFaceSetDetail(param.getFacesetToken(), param.getFaceOffset(), param.getFaceCount()));
		return result;
	}

	/**
	 * 合并人脸库
	 * @param param
	 * @return
	 * @throws FacePlatException 
	 */
	@RequestMapping(value = "merge")
	@ApiOperation(value="merge", notes="合并人脸库")
	@HystrixCommand
	public RestResult<SetMergeResult> merge(@Valid @ModelAttribute SetMergeParam param) throws FacePlatException {
		LOGGER.info("get faceset detail api");
		RestResult<SetMergeResult> result = new RestResult<>();
		result.setData(faceSetService.mergeFace(param.getFacesetToken1(), param.getFacesetToken2()));
		return result;
	}

}
