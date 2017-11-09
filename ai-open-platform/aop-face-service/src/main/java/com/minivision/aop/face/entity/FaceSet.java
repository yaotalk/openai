package com.minivision.aop.face.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@RedisHash("facesets")
@Getter
@Setter
public class FaceSet implements Serializable {

	private static final long serialVersionUID = 6298659607552057753L;

	@Id
	private String facesetToken;
	@Indexed
	private String outerId;
	private String displayName;
	@JsonIgnore
	private String owner;

	@Transient
	private List<Face> faces;
	
	private int capacity = 1000;

	@Override
	public String toString() {
		return "FaceSet [facesetToken=" + facesetToken + ", outerId=" + outerId + ", displayName=" + displayName
				+ ", faces=" + faces + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facesetToken == null) ? 0 : facesetToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FaceSet other = (FaceSet) obj;
		if (facesetToken == null) {
			if (other.facesetToken != null)
				return false;
		} else if (!facesetToken.equals(other.facesetToken))
			return false;
		return true;
	}

}
