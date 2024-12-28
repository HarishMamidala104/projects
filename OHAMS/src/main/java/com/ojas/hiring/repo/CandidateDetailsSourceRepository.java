package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.CandidateDetailsSource;

@Repository
public class CandidateDetailsSourceRepository {

  

    @Autowired
    private EntityManager entityManager;

    public List<CandidateDetailsSource> findAll() {
    	String nativeQuery = "SELECT  DISTINCT  id, source_name FROM config_candidatedetails_source_information";
        Query query = entityManager.createNativeQuery(nativeQuery);
        List<Object[]> resultList = query.getResultList();
        List<CandidateDetailsSource> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            CandidateDetailsSource source = new CandidateDetailsSource();
            source.setId((Integer) obj[0]);
            source.setSourceName((String) obj[1]);
            list.add(source);
        }
        return list;
    }
}