package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.CustomerTypeDto;

@Repository
public class CustomerTypeRepository {



    @Autowired
    private EntityManager entityManager;

    public List<CustomerTypeDto> findAll() {
    	String nativeQuery = "SELECT  DISTINCT  id, customer_type FROM config_customer_type";
        Query query = entityManager.createNativeQuery(nativeQuery);
        List<Object[]> resultList = query.getResultList();
        List<CustomerTypeDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
        	CustomerTypeDto source = new CustomerTypeDto();
            source.setId((Integer) obj[0]);
            source.setCustomerType((String) obj[1]);
            list.add(source);
        }
        return list;
    }
}
