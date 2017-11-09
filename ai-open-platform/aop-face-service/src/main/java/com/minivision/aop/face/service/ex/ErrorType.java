package com.minivision.aop.face.service.ex;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ErrorType {
	public static final int SERVER_ERROR = 0;

	public static final int FACE_NOT_EXIST = 1;

	public static final int FACESET_NOT_EXIST = 2;

	public static final int FACE_ADD_REPEATED = 3;

	public static final int FACESET_NOT_NULL = 5;
	
	public static final int FACESET_OUT_CAPACITY = 6;
	
	public static final int NO_FACE_DETECTED = 7;
	
	public static final int FACE_ALGO_ERROR = 100;
	
	public static final int ARGUMENT_ERROR = 201;
	

	private static final Map<Integer, String> DESC = new HashMap<>();
	static {
		try {
			Field[] fields = ErrorType.class.getDeclaredFields();
			for (Field f : fields) {
				if (Modifier.isPublic(f.getModifiers()) && f.getType() == int.class) {
					String name = f.getName();
					DESC.put((Integer) f.get(null), name);
					continue;
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException("init failed.", e);
		}
	}

	public static String getDesc(int type) {
		return DESC.get(type);
	}

}
