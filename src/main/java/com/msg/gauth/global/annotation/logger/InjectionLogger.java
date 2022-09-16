package com.msg.gauth.global.annotation.logger;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InjectionLogger implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext ac = event.getApplicationContext();
        Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("name = " + name);
            Object bean = ac.getBean(name);
            String classPath = bean.getClass().getName();
            try {
                System.out.println("classPath = " + classPath);
                if(!classPath.contains("gauth")){
                    continue;
                }
                Class<?> beanClass = Class.forName(classPath);
                Field[] fields = beanClass.getDeclaredFields();
                for (Field field : fields) {
                    System.out.println("field = " + field);
                    log4k log4k = field.getDeclaredAnnotation(log4k.class);
                    System.out.println("log4k = " + log4k);
                    if(log4k != null){
                        field.setAccessible(true);
                        field.set(bean, log);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
