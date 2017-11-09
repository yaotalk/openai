import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import javax.servlet.http.*

public class AccessLogFilterBean extends ZuulFilter {

    @Override
    String filterType() {
        return "post"
    }

    @Override
    int filterOrder() {
        return 0
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();

        System.out.println("REQUEST :: < " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("PARAMS  :: < " + context.getRequestQueryParams());
        System.out.println("RESPONSE:: > " + response.getStatus());

        return null;
    }

}

beans {
    accessLogFilterBean(AccessLogFilterBean) {
        new AccessLogFilterBean();
    }
}