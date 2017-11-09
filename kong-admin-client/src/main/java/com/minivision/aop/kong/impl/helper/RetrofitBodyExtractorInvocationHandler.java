package com.minivision.aop.kong.impl.helper;

import retrofit2.Call;
import retrofit2.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.minivision.aop.kong.exception.KongClientException;

/**
 * Created by vaibhav on 13/06/17.
 */
public class RetrofitBodyExtractorInvocationHandler implements InvocationHandler {

    private Object proxied;

    public RetrofitBodyExtractorInvocationHandler(Object proxied) {
        this.proxied = proxied;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method proxiedMethod = proxied.getClass().getMethod(methodName, parameterTypes);
        Call call = (Call) proxiedMethod.invoke(proxied, args);
        Response response = call.execute();
        if(!response.isSuccessful()) {
            throw new KongClientException(response.errorBody() != null ? response.errorBody().string() : String.valueOf(response.code()));
        }
        return response.body();
    }
}
