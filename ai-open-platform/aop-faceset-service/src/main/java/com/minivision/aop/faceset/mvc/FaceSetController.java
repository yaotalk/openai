package com.minivision.aop.faceset.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.minivision.aop.faceset.domain.Face;
import com.minivision.aop.faceset.domain.FaceSet;
import com.minivision.aop.faceset.mvc.ex.ServiceException;
import com.minivision.aop.faceset.rest.param.faceset.FacesetParam;
import com.minivision.aop.faceset.service.FaceService;
import com.minivision.aop.faceset.service.FaceSetService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faceset")
public class FaceSetController {

  @Autowired
  private FaceSetService faceSetService;

  @Autowired
  private FaceService faceService;

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView page() {
    return new ModelAndView("faceset/list");
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<FaceSet> list() {
    return this.faceSetService.findByFaceplat();
  }

  @PostMapping
  public String create(FaceSet set) {
    set.setCreateTime(new Date());
    set = this.faceSetService.create(set);
    if (set != null) {
      return "success";
    }
    return "failed";
  }

  @DeleteMapping
  public String delete(String token) {
    try {
      this.faceSetService.delete(token);
    } catch (ServiceException e) {
      return "failed";
    }
    return "success";
  }

  @PatchMapping
  public String update(FaceSet set) {
    this.faceSetService.update(set);
    return "success";
  }

  @GetMapping("face")
  public ModelAndView getFace(FacesetParam facesetParam) {
    String facesetToken = facesetParam.getFacesetToken();
    List<FaceSet> faceSets = this.faceSetService.findAll();
    faceSets = faceSets.stream().sorted((e1, e2) -> (e1.getName().compareTo(e2.getName())))
        .collect(Collectors.toList());
    if (StringUtils.isEmpty(facesetToken)) {
      if (faceSets.size() > 0) {
        facesetToken = faceSets.get(0).getToken();
      }
    }
    List<Face> faces = new ArrayList<>();
    long totalcout = 0;
    if (!StringUtils.isEmpty(facesetToken)) {
      facesetParam.setFacesetToken(facesetToken);
      FaceSet faceSet = faceSetService.find(facesetToken);
      if (faceSet != null) {
        Page<Face> pagefaces = faceService.findByFacesetId(facesetParam);
        totalcout = pagefaces.getTotalElements();
        if (pagefaces.getSize() > 0) {
          faces = pagefaces.getContent();
        }
      }
    }
    return new ModelAndView("faceset/facelist").addObject("faceSets", faceSets)
        .addObject("rows", faces).addObject("total", totalcout);
  }

  @RequestMapping("error")
  public String foo() {
    throw new RuntimeException("Expected exception in controller");
  }
}
