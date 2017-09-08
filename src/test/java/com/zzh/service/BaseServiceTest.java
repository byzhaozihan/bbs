package com.zzh.service;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

@SpringApplicationContext({"spring-service.xml"})
public class BaseServiceTest extends UnitilsTestNG{

    @SpringBean(value = "hibernateTemplate")
    public HibernateTemplate hibernateTemplate;
}
