package nextstep.mvc.tobe.handlerresolver;

import nextstep.mvc.tobe.handler.ControllerHandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerMappingAdapter implements HandlerResolver {
    private HandlerMapping handlerMapping;

    public HandlerMappingAdapter(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void initialize() {
        this.handlerMapping.initialize();
    }

    @Override
    public boolean support(HttpServletRequest req, HttpServletResponse resp) {
        return handlerMapping.getHandler(req.getRequestURI()) != null;
    }

    @Override
    public ControllerHandlerExecution getHandler(HttpServletRequest req) {
        Object target = null;
        try {
            target = handlerMapping.getHandler(req.getRequestURI()).getClass().getConstructor().newInstance();
        } catch (Exception e) {
            // TODO : throw exception
            e.printStackTrace();
        }

        Method method = target.getClass().getDeclaredMethods()[0];
        return new ControllerHandlerExecution(method, target);
    }
}