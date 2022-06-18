package com.example.SpringProject.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
public class DetermineSingletonBean implements BeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Scope annotation = AnnotationUtils.getAnnotation(bean.getClass(), Scope.class);
        if (annotation != null) {
            if(annotation.value().equals(ConfigurableBeanFactory.SCOPE_SINGLETON)){
                logger.info(beanName + " is SINGLETON Bean!");            }
        }
        return bean;
    }
}
