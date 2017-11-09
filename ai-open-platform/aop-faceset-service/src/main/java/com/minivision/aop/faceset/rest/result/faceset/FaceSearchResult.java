package com.minivision.aop.faceset.rest.result.faceset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaceSearchResult {

    private String facesetToken;
    private String faceToken;
    private String name;
    private String sex;
    private String idCard;
    private String imgpath;
    private double confidence;

}
