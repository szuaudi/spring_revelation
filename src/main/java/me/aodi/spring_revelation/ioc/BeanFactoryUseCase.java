package me.aodi.spring_revelation.ioc;

import me.aodi.spring_revelation.ioc.service.FXNewsProvider;
import me.aodi.spring_revelation.ioc.service.impl.DowJonesNewsListener;
import me.aodi.spring_revelation.ioc.service.impl.DowJonesNewsPersister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

@RunWith(JUnit4.class)
public class BeanFactoryUseCase {

    @Test
    public void bindViaXml() {
        BeanDefinitionRegistry beanRegistry = new DefaultListableBeanFactory();
        BeanFactory beanFactory = bindViaXml(beanRegistry);
        FXNewsProvider newsProvider = beanFactory.getBean(FXNewsProvider.class);
        newsProvider.getAndPersistNews();
    }

    public BeanFactory bindViaXml(BeanDefinitionRegistry beanRegistry) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanRegistry);
        beanDefinitionReader.loadBeanDefinitions("beans.xml");
        return (BeanFactory) beanRegistry;
        // 或者直接
//        return new XmlBeanFactory(new ClassPathResource("beans.xml"));
    }

    @Test
    public void bindViaCode() {
        BeanDefinitionRegistry beanRegistry = new DefaultListableBeanFactory();

        BeanFactory beanFactory  = bindViaCode(beanRegistry);
        FXNewsProvider newsProvider = beanFactory.getBean(FXNewsProvider.class);
        newsProvider.getAndPersistNews();
    }

    private static BeanFactory bindViaCode(BeanDefinitionRegistry beanRegistry) {
        RootBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class);
        RootBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class);
        RootBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class);
        // 将bean定义注册到容器
        beanRegistry.registerBeanDefinition("djNewsProvider", newsProvider);
        beanRegistry.registerBeanDefinition("djNewsListener", newsListener);
        beanRegistry.registerBeanDefinition("djNewsPersister", newsPersister);
        // 指定依赖关系
        // 1. 通过构造方法注入
        ConstructorArgumentValues constructorArgValues = new ConstructorArgumentValues();
        constructorArgValues.addIndexedArgumentValue(0, newsListener);
        constructorArgValues.addIndexedArgumentValue(1, newsPersister);
        newsProvider.setConstructorArgumentValues(constructorArgValues);
        // 2. 通过setter方法注入
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
        propertyValues.addPropertyValue("newsPersister", newsPersister);
        newsProvider.setPropertyValues(propertyValues);

        return (BeanFactory) beanRegistry;
    }
}
